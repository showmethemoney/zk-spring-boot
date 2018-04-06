package zk.springboot.service;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class TestService
{
	public Date getTime() {
		return new Date();
	}
}
