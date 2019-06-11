package com.magasinudes.microservice.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "resource_category_statuses", schema = "public")
public class ResourceCategoryStatus extends AuditModel {
    @Id
    @GeneratedValue(generator = "resource_category_status_generator")
    @SequenceGenerator(
            name = "resource_category_status_generator",
            sequenceName = "resource_category_status_sequence",
            initialValue = 1
    )
    private Long id;

    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    // ------------
    // Associations
    // ------------

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resource_category_id", nullable = false)
    private ResourceCategory resourceCategory;

    @OneToMany(mappedBy = "status", targetEntity = Resource.class)
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

    public ResourceCategory getResourceCategory() {
        return resourceCategory;
    }

    public void setResourceCategory(ResourceCategory category) {
        this.resourceCategory = category;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void addResource(Resource resource) {
        this.resources.add(resource);
        resource.setStatus(this);
    }
}
