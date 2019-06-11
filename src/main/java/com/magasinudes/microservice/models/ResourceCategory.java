package com.magasinudes.microservice.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "resource_categories", schema = "public")
public class ResourceCategory extends AuditModel {
    @Id
    @GeneratedValue(generator = "resource_category_generator")
    @SequenceGenerator(
            name = "resource_category_generator",
            sequenceName = "resource_category_sequence",
            initialValue = 1
    )
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    // ------------
    // Associations
    // ------------

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "outlet_id", nullable = false)
    private Outlet outlet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ResourceCategory parent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resource_category_type_id", nullable = false)
    private ResourceCategoryType type;

    @OneToMany(mappedBy = "parent", orphanRemoval = true, targetEntity = ResourceCategory.class)
    private Set<ResourceCategory> children = new HashSet<>();

    @OneToMany(mappedBy = "resourceCategory", orphanRemoval = true, targetEntity = ResourceCategoryStatus.class)
    private Set<ResourceCategoryStatus> statuses = new HashSet<>();

    @OneToMany(mappedBy = "resourceCategory", orphanRemoval = true, targetEntity = CostType.class)
    private Set<CostType> costTypes = new HashSet<>();

    @OneToMany(mappedBy = "category", orphanRemoval = true, targetEntity = Resource.class)
    private Set<Resource> resources = new HashSet<>();

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

    public Outlet getOutlet() {
        return outlet;
    }

    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    public ResourceCategory getParent() {
        return parent;
    }

    public void setParent(ResourceCategory parent) {
        this.parent = parent;
    }

    public ResourceCategoryType getType() {
        return type;
    }

    public void setType(ResourceCategoryType type) {
        this.type = type;
    }

    public Set<ResourceCategory> getChildren() {
        return children;
    }

    public void addChildCategory(ResourceCategory child) {
        this.children.add(child);
        child.setParent(this);
    }

    public Set<ResourceCategoryStatus> getStatuses() {
        return statuses;
    }

    public void addStatus(ResourceCategoryStatus status) {
        this.statuses.add(status);
        status.setResourceCategory(this);
    }

    public Set<CostType> getCostTypes() {
        return costTypes;
    }

    public void addCostType(CostType costType) {
        this.costTypes.add(costType);
        costType.setResourceCategory(this);
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void addResource(Resource resource) {
        this.resources.add(resource);
        resource.setCategory(this);
    }
}
