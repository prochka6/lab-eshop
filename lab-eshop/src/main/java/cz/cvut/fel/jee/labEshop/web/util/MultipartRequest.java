package cz.cvut.fel.jee.labEshop.web.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class MultipartRequest extends org.primefaces.webapp.MultipartRequest {

	private static final Logger logger = Logger.getLogger(MultipartRequest.class.getName());

	private Map<String, List<String>> formParams;

	private Map<String, List<FileItem>> fileParams;

	private Map<String, String[]> parameterMap;

	public MultipartRequest(HttpServletRequest request, ServletFileUpload servletFileUpload) throws IOException {
		super(request, new ServletFileUpload() {
			@SuppressWarnings("rawtypes")
			@Override
			public List parseRequest(HttpServletRequest request) throws FileUploadException {
				return Collections.emptyList();
			}
		});
		formParams = new LinkedHashMap<String, List<String>>();
		fileParams = new LinkedHashMap<String, List<FileItem>>();

		parseRequest(request, servletFileUpload);
	}

	@SuppressWarnings("unchecked")
	private void parseRequest(HttpServletRequest request, ServletFileUpload servletFileUpload) throws IOException {
		try {
			List<FileItem> fileItems = servletFileUpload.parseRequest(request);

			for (FileItem item : fileItems) {
				if (item.isFormField())
					addFormParam(item);
				else
					addFileParam(item);
			}
		} catch (FileUploadException e) {
			logger.severe("Error in parsing fileupload request");
			throw new IOException(e.getMessage());
		}
	}

	private void addFileParam(FileItem item) {
		if (fileParams.containsKey(item.getFieldName())) {
			fileParams.get(item.getFieldName()).add(item);
		} else {
			List<FileItem> items = new ArrayList<FileItem>();
			items.add(item);
			fileParams.put(item.getFieldName(), items);
		}
	}

	private void addFormParam(FileItem item) {
		if (formParams.containsKey(item.getFieldName())) {
			formParams.get(item.getFieldName()).add(item.getString());
		} else {
			List<String> items = new ArrayList<String>();
			try {
				items.add(item.getString("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.log(Level.SEVERE,
						"Unsupported encoding while encoding multipart/form-data field: " + item.getFieldName());
			}
			formParams.put(item.getFieldName(), items);
		}
	}

	@Override
	public String getParameter(String name) {
		if (formParams.containsKey(name)) {
			List<String> values = formParams.get(name);
			if (values.isEmpty())
				return "";
			else
				return values.get(0);
		} else {
			return super.getParameter(name);
		}
	}

	@Override
	public Map getParameterMap() {
		if (parameterMap == null) {
			Map<String, String[]> map = new LinkedHashMap<String, String[]>();

			for (String formParam : formParams.keySet()) {
				map.put(formParam, formParams.get(formParam).toArray(new String[0]));
			}

			map.putAll(super.getParameterMap());

			parameterMap = Collections.unmodifiableMap(map);
		}

		return parameterMap;
	}

	@Override
	public Enumeration getParameterNames() {
		Set<String> paramNames = new LinkedHashSet<String>();
		paramNames.addAll(formParams.keySet());

		Enumeration<String> original = super.getParameterNames();
		while (original.hasMoreElements()) {
			paramNames.add(original.nextElement());
		}

		return Collections.enumeration(paramNames);
	}

	@Override
	public String[] getParameterValues(String name) {
		if (formParams.containsKey(name)) {
			List<String> values = formParams.get(name);
			if (values.isEmpty())
				return new String[0];
			else
				return values.toArray(new String[values.size()]);
		} else {
			return super.getParameterValues(name);
		}
	}

	public FileItem getFileItem(String name) {
		if (fileParams.containsKey(name)) {
			List<FileItem> items = fileParams.get(name);

			return items.get(0);
		} else {
			return null;
		}
	}
}