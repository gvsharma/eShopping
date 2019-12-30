package com.pj.eshopping.model.product;

import com.pj.eshopping.audit.AbstractAuditingEntity;
import com.pj.eshopping.model.category.Category;
import com.pj.eshopping.model.inventory.ProductInventory;
import com.pj.eshopping.model.manufacturer.Manufacturer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Cache(region = "productCache", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "product")
public class Product extends AbstractAuditingEntity implements Serializable
{
	private static final long serialVersionUID = 673660797966130931L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", length = 10000)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
	private Manufacturer manufacturer;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "product")
	private ProductInventory productInventory;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "price_id")
	private Price price;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
	private List<Photo> photoList = new ArrayList<>();

	@Override
	public String toString()
	{
		return "Product{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", category=" + category +
				'}';
	}
}