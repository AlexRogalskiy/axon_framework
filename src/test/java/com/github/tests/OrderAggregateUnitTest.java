package com.github.tests;

import com.github.commandmodel.order.OrderAggregate;
import com.github.coreapi.commands.CreateOrderCommand;
import com.github.coreapi.events.OrderCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class OrderAggregateUnitTest {

    private static final String ORDER_ID = UUID.randomUUID().toString();
    String product = "Deluxe Chair";

    private FixtureConfiguration<OrderAggregate> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(OrderAggregate.class);
    }

    @Test
    public void giveNoPriorActivity_whenCreateOrderCommand_thenShouldPublishOrderCreatedEvent() {
        fixture.givenNoPriorActivity()
                .when(new CreateOrderCommand(ORDER_ID, product))
                .expectEvents(new OrderCreatedEvent(ORDER_ID));
    }
}


