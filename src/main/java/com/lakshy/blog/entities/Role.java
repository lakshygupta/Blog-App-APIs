package com.lakshy.blog.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Roles")
@Getter
@Setter
public class Role {
	@Id
	private int id;
	
	private String name;
	
}
