package com.thinkbox.m2.m2_questdb.service;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class QuestDBComponent {
	@PostConstruct
	public void init() {
		System.out.println("TEST");
	}
}