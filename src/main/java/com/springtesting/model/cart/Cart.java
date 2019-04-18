package com.springtesting.model.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springtesting.model.AbstractAuditingEntity;
import com.springtesting.model.user.UserProfile;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "cart")
public class Cart  extends AbstractAuditingEntity
{
    private static final long serialVersionUID = 6294902210705780249L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_profile_id")
    @JsonIgnoreProperties(value = {"addresses"})
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "cart_status")
    private CartStatus cartStatus;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<CartProduct> cartProducts=new ArrayList<>();

    @Override
    public String toString()
    {
        return "Cart{" +
                "id=" + id +
                ", userProfile=" + userProfile +
                ", cartStatus=" + cartStatus +
                '}';
    }
}
