/**
 * Utility methods. Based on jQuery.
 */
var LabEshop = {

	/**
	 * Scrolls to Top of the page Window.
	 */
	scrollTop : function() {
		jQuery("html, body").animate({
			scrollTop : 0
		});
	},

	/**
	 * Scrolls to top of the html element specified by given id.
	 * 
	 * @param id
	 *            the id of html element
	 */
	scrollTo : function(id) {
		jQuery("html, body").animate({
			scrollTop : jQuery(document.getElementById(id)).offset().top
		});
	}

};
