package com.practice.reviewms.review.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

	//creating queue
	@Bean
	public Queue companyRatingQueue() {
		return new Queue("companyRatingQueue");
	}
	
	//serialize and deserialize message
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	//helper class handles creation and release of message
	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionfactory) {
		RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionfactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
	
}
