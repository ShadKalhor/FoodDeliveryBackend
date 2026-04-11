package com.example.FoodDeliveryBackend.infrastructure.config;

import com.example.FoodDeliveryBackend.domain.enums.*;
import com.example.FoodDeliveryBackend.domain.model.GeoPoint;
import com.example.FoodDeliveryBackend.domain.model.Pricing;
import com.example.FoodDeliveryBackend.domain.model.Timestamps;
import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.*;
import com.example.FoodDeliveryBackend.infrastructure.persistence.repository.*;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Profile("dev")
@Configuration
public class DevDataSeeder {

    @Bean
    CommandLineRunner seedData(
            AccountRepositoryJPA AccountRepository,
            RestaurantRepositoryJPA restaurantRepository,
            CustomerAddressRepositoryJPA customerAddressRepository,
            MenuItemRepositoryJPA menuItemRepository,
            OrderRepositoryJPA orderRepository,
            OrderItemRepositoryJPA orderItemRepository,
            DriverRepositoryJPA driverRepository,
            DeliveryRepositoryJPA deliveryRepository
    ) {
        return args -> {
            if (AccountRepository.count() > 0) {
                return;
            }

            Faker faker = new Faker();
            Random random = new Random();

            List<Account> customers = new ArrayList<>();
            List<Account> owners = new ArrayList<>();
            List<Account> driverAccounts = new ArrayList<>();

            for (int i = 0; i < 20; i++) {
                Account Account = new Account();
                Account.setRole(Roles.CUSTOMER);
                Account.setName(faker.name().fullName());
                Account.setPhone("07" + (100000000 + random.nextInt(899999999)));
                Account.setEmail("customer" + i + "@test.com");
                Account.setPassword("$2a$10$examplehashedpassword");
                Account.setStatus(UserStatus.ONLINE);
                Account.setCreatedAt(Instant.now());
                customers.add(Account);
            }

            for (int i = 0; i < 5; i++) {
                Account Account = new Account();
                Account.setRole(Roles.RESTAURANT_ADMIN);
                Account.setName(faker.name().fullName());
                Account.setPhone("07" + (100000000 + random.nextInt(899999999)));
                Account.setEmail("owner" + i + "@test.com");
                Account.setPassword("$2a$10$examplehashedpassword");
                Account.setStatus(UserStatus.ONLINE);
                Account.setCreatedAt(Instant.now());
                owners.add(Account);
            }

            for (int i = 0; i < 5; i++) {
                Account Account = new Account();
                Account.setRole(Roles.DRIVER);
                Account.setName(faker.name().fullName());
                Account.setPhone("07" + (100000000 + random.nextInt(899999999)));
                Account.setEmail("driver" + i + "@test.com");
                Account.setPassword("$2a$10$examplehashedpassword");
                Account.setStatus(UserStatus.ONLINE);
                Account.setCreatedAt(Instant.now());
                driverAccounts.add(Account);
            }

            customers = AccountRepository.saveAll(customers);
            owners = AccountRepository.saveAll(owners);
            driverAccounts = AccountRepository.saveAll(driverAccounts);

            List<CustomerAddress> addresses = new ArrayList<>();
            for (Account customer : customers) {
                int count = 1 + random.nextInt(2);
                for (int i = 0; i < count; i++) {
                    CustomerAddress address = new CustomerAddress();
                    address.setUser(customer);
                    address.setLabel(i == 0 ? "Home" : "Work");
                    address.setAddress(faker.address().fullAddress());

                    GeoPoint point = new GeoPoint();
                    point.setLat(bigDecimalBetween(36.0, 37.5, random));
                    point.setLng(bigDecimalBetween(43.0, 45.0, random));
                    address.setLocation(point);

                    addresses.add(address);
                }
            }
            addresses = customerAddressRepository.saveAll(addresses);

            List<Restaurant> restaurants = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Restaurant restaurant = new Restaurant();
                restaurant.setOwnerUser(owners.get(i));
                restaurant.setName(faker.company().name());
                restaurant.setDescription(faker.lorem().sentence(8));
                restaurant.setAddress(faker.address().fullAddress());

                GeoPoint point = new GeoPoint();
                point.setLat(bigDecimalBetween(36.0, 37.5, random));
                point.setLng(bigDecimalBetween(43.0, 45.0, random));
                restaurant.setLocation(point);

                restaurant.setOpen(true);
                restaurant.setMinOrderAmount(new BigDecimal("5.00"));
                restaurant.setAvgPrepTimeInMins(15 + random.nextInt(30));
                restaurant.setCreatedAt(Instant.now());

                restaurants.add(restaurant);
            }
            restaurants = restaurantRepository.saveAll(restaurants);

            List<MenuItem> menuItems = new ArrayList<>();
            MenuCategories[] categories = MenuCategories.values();

            for (Restaurant restaurant : restaurants) {
                for (int i = 0; i < 8; i++) {
                    MenuItem item = new MenuItem();
                    item.setRestaurant(restaurant);
                    item.setName(faker.food().ingredient());
                    item.setDescription(faker.lorem().sentence(6));
                    item.setPrice(bigDecimalBetween(3.0, 25.0, random));
                    item.setAvailable(random.nextBoolean());
                    item.setCategory(categories[random.nextInt(categories.length)]);
                    item.setImageUrl("https://picsum.photos/seed/" + faker.number().randomNumber() + "/300/200");
                    item.setCreatedAt(Instant.now());

                    menuItems.add(item);
                }
            }
            menuItems = menuItemRepository.saveAll(menuItems);

            List<Driver> drivers = new ArrayList<>();
            VehicleType[] vehicleTypes = VehicleType.values();

            for (Account driverAccount : driverAccounts) {
                Driver driver = new Driver();
                driver.setUser(driverAccount);
                driver.setVehicleType(vehicleTypes[random.nextInt(vehicleTypes.length)]);
                driver.setOnline(random.nextBoolean());

                GeoPoint point = new GeoPoint();
                point.setLat(bigDecimalBetween(36.0, 37.5, random));
                point.setLng(bigDecimalBetween(43.0, 45.0, random));
                driver.setCurrentLocation(point);

                driver.setAvgRating(bigDecimalBetween(3.5, 5.0, random));
                drivers.add(driver);
            }
            drivers = driverRepository.saveAll(drivers);

            List<Order> orders = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                Account customer = customers.get(random.nextInt(customers.size()));
                Restaurant restaurant = restaurants.get(random.nextInt(restaurants.size()));

                List<CustomerAddress> customerAddresses = addresses.stream()
                        .filter(a -> a.getUser().getId().equals(customer.getId()))
                        .toList();

                if (customerAddresses.isEmpty()) {
                    continue;
                }

                Order order = new Order();
                order.setCustomer(customer);
                order.setRestaurant(restaurant);
                order.setDeliveryAddress(customerAddresses.get(random.nextInt(customerAddresses.size())));
                order.setStatus(OrderStatus.PLACED);
                order.setPaymentStatus(PaymentStatus.PENDING);

                Pricing pricing = new Pricing();
                pricing.setItemsSubtotal(BigDecimal.ZERO);
                pricing.setDeliveryFee(new BigDecimal("2.50"));
                pricing.setServiceFee(new BigDecimal("1.50"));
                pricing.setTax(BigDecimal.ZERO);
                pricing.setTotal(BigDecimal.ZERO);
                order.setPricing(pricing);

                Timestamps timestamps = new Timestamps();
                timestamps.setCreatedAt(Instant.now());
                timestamps.setAcceptedAt(null);
                timestamps.setReadyAt(null);
                timestamps.setPickedUpAt(null);
                timestamps.setDeliveredAt(null);
                timestamps.setCancelledAt(null);
                order.setTimestamps(timestamps);

                orders.add(order);
            }
            orders = orderRepository.saveAll(orders);

            List<OrderItem> orderItems = new ArrayList<>();
            for (Order order : orders) {
                List<MenuItem> restaurantItems = menuItems.stream()
                        .filter(mi -> mi.getRestaurant().getId().equals(order.getRestaurant().getId()))
                        .toList();

                int itemCount = 1 + random.nextInt(3);
                BigDecimal subtotal = BigDecimal.ZERO;

                for (int i = 0; i < itemCount; i++) {
                    MenuItem menuItem = restaurantItems.get(random.nextInt(restaurantItems.size()));
                    int quantity = 1 + random.nextInt(3);
                    BigDecimal lineTotal = menuItem.getPrice().multiply(BigDecimal.valueOf(quantity));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setMenuItem(menuItem);
                    orderItem.setNameSnapshot(menuItem.getName());
                    orderItem.setUnitPriceSnapshot(menuItem.getPrice());
                    orderItem.setQuantity(quantity);
                    orderItem.setLineTotal(lineTotal);

                    orderItems.add(orderItem);
                    subtotal = subtotal.add(lineTotal);
                }

                Pricing pricing = order.getPricing();
                pricing.setItemsSubtotal(subtotal);
                pricing.setTax(subtotal.multiply(new BigDecimal("0.10")).setScale(2, RoundingMode.HALF_UP));
                pricing.setTotal(
                        pricing.getItemsSubtotal()
                                .add(pricing.getDeliveryFee())
                                .add(pricing.getServiceFee())
                                .add(pricing.getTax())
                                .setScale(2, RoundingMode.HALF_UP)
                );
                order.setPricing(pricing);
            }

            orderItems = orderItemRepository.saveAll(orderItems);
            orders = orderRepository.saveAll(orders);

            List<Delivery> deliveries = new ArrayList<>();
            DeliveryStatus[] deliveryStatuses = DeliveryStatus.values();

            for (int i = 0; i < Math.min(10, orders.size()); i++) {
                Order order = orders.get(i);
                Driver driver = drivers.get(random.nextInt(drivers.size()));

                Delivery delivery = new Delivery();
                delivery.setOrder(order);
                delivery.setDriver(driver);
                delivery.setStatus(deliveryStatuses[random.nextInt(deliveryStatuses.length)]);

                GeoPoint pickup = new GeoPoint();
                pickup.setLat(order.getRestaurant().getLocation().getLat());
                pickup.setLng(order.getRestaurant().getLocation().getLng());
                delivery.setPickupLocation(pickup);

                GeoPoint dropoff = new GeoPoint();
                dropoff.setLat(order.getDeliveryAddress().getLocation().getLat());
                dropoff.setLng(order.getDeliveryAddress().getLocation().getLng());
                delivery.setDropOffLocation(dropoff);

                delivery.setAssignedAt(Instant.now());
                delivery.setAcceptedAt(Instant.now());
                delivery.setPickedUpAt(null);
                delivery.setDeliveredAt(null);

                deliveries.add(delivery);
            }

            deliveries = deliveryRepository.saveAll(deliveries);

            System.out.println("Seeded development data successfully.");
        };
    }

    private static BigDecimal bigDecimalBetween(double min, double max, Random random) {
        double value = min + (max - min) * random.nextDouble();
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}