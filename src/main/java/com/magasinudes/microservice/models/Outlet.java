package com.magasinudes.microservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "outlets", schema = "public")
public class Outlet extends AuditModel {
    @Id
    @GeneratedValue(generator = "outlets_generator")
    @SequenceGenerator(
            name = "outlets_generator",
            sequenceName = "outlets_sequence",
            initialValue = 1
    )
    private Long id;

    @NotBlank
    @Size(min = 3, max = 128)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name= "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name= "close_time", nullable = false)
    private LocalTime closeTime;

    // ------------
    // Associations
    // ------------

    @OneToMany(mappedBy = "outlet", orphanRemoval = true, targetEntity = ResourceCategory.class)
    @JsonIgnore
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

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public Set<ResourceCategory> getCategories() {
        return categories;
    }

    public void addCategory(ResourceCategory category) {
        this.categories.add(category);
        category.setOutlet(this);
    }
}
