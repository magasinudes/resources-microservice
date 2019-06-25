package com.magasinudes.microservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cost_types", schema = "public")
public class CostType extends AuditModel {
    @Id
    @GeneratedValue(generator = "cost_type_generator")
    @SequenceGenerator(
            name = "cost_type_generator",
            sequenceName = "cost_type_sequence",
            initialValue = 1
    )
    private Long id;

    @NotBlank
    @Size(min = 1, max = 64)
    @Column(name = "symbol", nullable = false)
    private String symbol;

    @Column(name = "description")
    private String description;

    // ------------
    // Associations
    // ------------

    @OneToMany(mappedBy = "costType", targetEntity = Resource.class)
    @JsonIgnore
    private Set<Resource> resources = new HashSet<>();

    // ------------
    // Helpers
    // ------------

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void addResource(Resource resource) {
        this.resources.add(resource);
        resource.setCostType(this);
    }
}
