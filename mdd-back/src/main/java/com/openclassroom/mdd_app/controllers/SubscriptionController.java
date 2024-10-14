package com.openclassroom.mdd_app.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.mdd_app.entities.Subscription;
import com.openclassroom.mdd_app.entities.User;
import com.openclassroom.mdd_app.payload.response.MessageResponseHandler;
import com.openclassroom.mdd_app.services.SubscriptionService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/subscription")
public class SubscriptionController {
    public SubscriptionService subscriptionService;
	
	public SubscriptionController(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

    @PostMapping("/subscribe/{topicId}")
    public ResponseEntity<MessageResponseHandler> subscribe(@PathVariable Long topicId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUser = (UserDetails) authentication.getPrincipal();
        String mail = currentUser.getUsername();
                
        subscriptionService.subscribe(mail, topicId);
        
        return ResponseEntity.ok(new MessageResponseHandler("Vous êtes désormais abonné à ce thème !"));		
	}

    @DeleteMapping("/unsubscribe/{subId}")
    public ResponseEntity<MessageResponseHandler> unsubscribe(@PathVariable Long subId) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUser = (UserDetails) authentication.getPrincipal();
        String mail = currentUser.getUsername();
		
        subscriptionService.unsubscribe(subId, mail);

        return ResponseEntity.ok(new MessageResponseHandler("Vous êtes bien désabonné"));		
		
	}

    @GetMapping("/user/all")
    public ResponseEntity<List<Subscription>> getAllSubscriptionsByUserId() {
		 
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     User currentUser = (User) authentication.getPrincipal();
	     Long userId = currentUser.getId();
	     
	     List<Subscription> subscriptions = subscriptionService.getAllSubscriptionsByUserId(userId);
	     return ResponseEntity.ok(subscriptions);
	     
	 }
}
