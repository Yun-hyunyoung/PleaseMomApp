package com.mom.project.pleasemom.dto;

import java.io.Serializable;

public class MemberDTO implements Serializable{
	private int mem_num;
	private String mem_id;
	private String mem_passwd;
	private String mem_name;
	private String mem_birth;
	private String mem_phone;
	private String mem_picture;
	private String mem_email_ck;
	private String mem_phone_ck;
	private String mem_case;
	
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_passwd() {
		return mem_passwd;
	}
	public void setMem_passwd(String mem_passwd) {
		this.mem_passwd = mem_passwd;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_birth() {
		return mem_birth;
	}
	public void setMem_birth(String mem_birth) {
		this.mem_birth = mem_birth;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public String getMem_picture() {
		return mem_picture;
	}
	public void setMem_picture(String mem_picture) {
		this.mem_picture = mem_picture;
	}
	public String getMem_email_ck() {
		return mem_email_ck;
	}
	public void setMem_email_ck(String mem_email_ck) {
		this.mem_email_ck = mem_email_ck;
	}
	public String getMem_phone_ck() {
		return mem_phone_ck;
	}
	public void setMem_phone_ck(String mem_phone_ck) {
		this.mem_phone_ck = mem_phone_ck;
	}
	public String getMem_case() {
		return mem_case;
	}
	public void setMem_case(String mem_case) {
		this.mem_case = mem_case;
	}
	public MemberDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MemberDTO(int mem_num, String mem_id, String mem_passwd, String mem_name, String mem_birth, String mem_phone,
			String mem_picture, String mem_email_ck, String mem_phone_ck, String mem_case) {
		super();
		this.mem_num = mem_num;
		this.mem_id = mem_id;
		this.mem_passwd = mem_passwd;
		this.mem_name = mem_name;
		this.mem_birth = mem_birth;
		this.mem_phone = mem_phone;
		this.mem_picture = mem_picture;
		this.mem_email_ck = mem_email_ck;
		this.mem_phone_ck = mem_phone_ck;
		this.mem_case = mem_case;
	}
	@Override
	public String toString() {
		return "MemberDTO [mem_num=" + mem_num + ", mem_id=" + mem_id + ", mem_passwd=" + mem_passwd + ", mem_name="
				+ mem_name + ", mem_birth=" + mem_birth + ", mem_phone=" + mem_phone + ", mem_picture=" + mem_picture
				+ ", mem_email_ck=" + mem_email_ck + ", mem_phone_ck=" + mem_phone_ck + ", mem_case=" + mem_case + "]";
	}
}
