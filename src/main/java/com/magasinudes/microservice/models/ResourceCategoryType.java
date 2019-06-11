package com.magasinudes.microservice.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "resource_category_types", schema = "public")
public class ResourceCategoryType extends AuditModel {
    @Id
    @GeneratedValue(generator = "resource_category_type_generator")
    @SequenceGenerator(
            name = "resource_category_type_generator",
            sequenceName = "resource_category_type_sequence",
            initialValue = 1
    )
    private Long id;

    @NotBlank
    @Size(min = 3, max = 32)
    @Column(name = "name", nullable = false)
    private String name;

    // ------------
    // Associations
    // ------------

    @OneToMany(mappedBy = "type", targetEntity = ResourceCategory.class)
    private Set<ResourceCategory> categories = new HashSet<>();

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

    public Set<ResourceCategory> getCategories() {
        return categories;
    }

    public void addCategory(ResourceCategory category) {
        this.categories.add(category);
        category.setType(this);
    }
}
