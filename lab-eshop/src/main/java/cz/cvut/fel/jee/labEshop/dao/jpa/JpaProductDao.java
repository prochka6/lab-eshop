package cz.cvut.fel.jee.labEshop.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cz.cvut.fel.jee.labEshop.dao.IProductDao;
import cz.cvut.fel.jee.labEshop.filter.EntityFilter;
import cz.cvut.fel.jee.labEshop.filter.ProductSearchFilter;
import cz.cvut.fel.jee.labEshop.filter.SortModel.Property;
import cz.cvut.fel.jee.labEshop.filter.SortOrder;
import cz.cvut.fel.jee.labEshop.model.Money_;
import cz.cvut.fel.jee.labEshop.model.Product;
import cz.cvut.fel.jee.labEshop.model.ProductAvailability;
import cz.cvut.fel.jee.labEshop.model.Product_;

/**
 * Jpa implementation of {@linkplain IProductDao}.
 * 
 * @see IProductDao
 * @see JpaBaseDao
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public class JpaProductDao extends JpaBaseDao<Product> implements IProductDao {

	@Override
	public List<Product> findLatestsProducts(EntityFilter filter) {
		TypedQuery<Product> query = em.createNamedQuery("Product.getLatests", Product.class).setParameter("date",
				new Date());
		return applyPaging(query, filter).getResultList();
	}

	@Override
	public List<Product> find(ProductSearchFilter filter) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Product> query = builder.createQuery(Product.class);
		Root<Product> root = query.from(Product.class);
		// Prefetch because of EAGER initialization.
		root.fetch(Product_.category);
		root.fetch(Product_.brand);

		Predicate predicate = builder.conjunction();

		if (filter.getPriceMin() != null) {
			predicate = builder.and(predicate,
					builder.ge(root.get(Product_.price).get(Money_.amount), filter.getPriceMin()));
		}
		if (filter.getPriceMax() != null) {
			predicate = builder.and(predicate,
					builder.le(root.get(Product_.price).get(Money_.amount), filter.getPriceMax()));
		}
		if (filter.getInStock() != null && filter.getInStock()) {
			predicate = builder.and(predicate,
					builder.equal(root.get(Product_.availability), ProductAvailability.IN_STOCK));
		}
		if (filter.getCategoryIds() != null && !filter.getCategoryIds().isEmpty()) {
			predicate = builder.and(predicate, root.get(Product_.category).in(filter.getCategoryIds()));
		}
		if (filter.getBrandIds() != null && !filter.getBrandIds().isEmpty()) {
			predicate = builder.and(predicate, root.get(Product_.brand).in(filter.getBrandIds()));
		}

		// default publish and discard date
		// TODO: add the to filter ?
		predicate = builder.and(predicate, builder.greaterThan(root.get(Product_.discardDate), new Date()));
		predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get(Product_.publishDate), new Date()));

		query.distinct(true);
		query.select(root);
		query.where(predicate);

		if (filter.getSortModel() != null) {
			for (Property sort : filter.getSortModel()) {
				if ("title".equals(sort.getKey())) {
					if (sort.getSortOrder() == SortOrder.ASCENDING) {
						query.orderBy(builder.asc(root.get(Product_.title)));
					} else {
						query.orderBy(builder.desc(root.get(Product_.title)));
					}
				} else if ("price".equals(sort.getKey())) {
					if (sort.getSortOrder() == SortOrder.ASCENDING) {
						query.orderBy(builder.asc(root.get(Product_.price).get(Money_.amount)));
					} else {
						query.orderBy(builder.desc(root.get(Product_.price).get(Money_.amount)));
					}
				}
			}
		}

		return applyPaging(em.createQuery(query), filter).getResultList();
	}

	@Override
	public Product findProductByCode(String code) {
		List<Product> result = em.createNamedQuery("Product.getByCode", Product.class).setParameter("code", code)
				.getResultList();

		if (result.isEmpty()) {
			return null;
		}

		// Expected max 1 value because of DB uniqueness.
		return result.get(0);
	}
}
