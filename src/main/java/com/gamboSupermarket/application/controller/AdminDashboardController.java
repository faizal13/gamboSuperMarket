package com.gamboSupermarket.application.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gamboSupermarket.application.dto.User;
import com.gamboSupermarket.application.model.AdminData;
import com.gamboSupermarket.application.model.CustomResponse;
import com.gamboSupermarket.application.model.Customer;
import com.gamboSupermarket.application.model.EmptyJsonResponse;
import com.gamboSupermarket.application.model.GetOrdersResponse;
import com.gamboSupermarket.application.model.SingleObjectRequest;
import com.gamboSupermarket.application.services.IOrderService;
import com.gamboSupermarket.application.services.UserService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(path = "/api/admin")
public class AdminDashboardController {

	@Autowired
	private IOrderService orderService;

	@Autowired
	private UserService userService;

	@GetMapping(path = { "/getAllOrders" })
	public ResponseEntity<CustomResponse<GetOrdersResponse>> getAllOrders(
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize)
			throws IOException {

		GetOrdersResponse orderInfoList = orderService.getAllOrders(pageNo, pageSize);
		CustomResponse<GetOrdersResponse> response = new CustomResponse<>(200, "Success", orderInfoList);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping(path = { "/getData" })
	public ResponseEntity<CustomResponse<AdminData>> getData() throws IOException {

		AdminData orderInfoList = orderService.getData();
		CustomResponse<AdminData> response = new CustomResponse<>(200, "Success", orderInfoList);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(path = { "/updateStatus" })
	public ResponseEntity<CustomResponse<Object>> updateStatus(@RequestBody SingleObjectRequest request)
			throws IOException {

		orderService.updateStatus(request);
		CustomResponse<Object> response = new CustomResponse<>(200, "status updated successfully",
				new EmptyJsonResponse());
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping(path = { "/getCustomers" })
	public ResponseEntity<CustomResponse<List<Customer>>> getCustomers() throws IOException {
		List<User> users = userService.findAllUsers();
		List<Customer> allCutomers = new ArrayList<>();
		users.stream().forEach(user -> {
			Customer customer = new Customer();
			customer.setId(user.getId());
			customer.setMobileNumber(user.getMobileNumber());
			customer.setName(user.getName());
			customer.setEmail(user.getUsername());
			allCutomers.add(customer);
		});

		CustomResponse<List<Customer>> response = new CustomResponse<>(200, "Success", allCutomers);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
