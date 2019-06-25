package com.magasinudes.microservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "resources", schema = "public")
public class Resource extends AuditModel {
    @Id
    @GeneratedValue(generator = "resource_generator")
    @SequenceGenerator(
            name = "resource_generator",
            sequenceName = "resource_sequence",
            initialValue = 1
    )
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "cost", precision = 10, scale = 2)
    private Float cost;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // ------------
    // Associations
    // ------------

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resource_category_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ResourceCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_category_status_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ResourceCategoryStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cost_type_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CostType costType;

    // TODO: OneToMany associations for reservations, orders and borrows.

    // ------------
    // Helpers
    // ------------

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void decrementQuantity() {
        this.quantity--;
    }

    public ResourceCategory getCategory() {
        return category;
    }

    public void setCategory(ResourceCategory category) {
        this.category = category;
    }

    public ResourceCategoryStatus getStatus() {
        return status;
    }

    public void setStatus(ResourceCategoryStatus status) {
        this.status = status;
    }

    public CostType getCostType() {
        return costType;
    }

    public void setCostType(CostType costType) {
        this.costType = costType;
    }
}
