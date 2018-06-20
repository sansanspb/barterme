package ru.iqdevelop.barterme.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class RoleEntity {

	@Id
	@SequenceGenerator(name = "roles_seq", sequenceName = "roles_role_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "title")
	private String title;

	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private List<UserEntity> users = new ArrayList<>();

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String role) {
		this.title = role;
	}

}
