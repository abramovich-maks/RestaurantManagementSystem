package com.rms.feature;

import com.rms.BaseIntegrationTest;
import com.rms.infrastructure.menu.controller.dto.CreateMenuItemResponse;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MenuItemsCrudTest extends BaseIntegrationTest {

    @Test
    public void should_handle_full_crud_flow_for_menu_items() throws Exception {
        // 1. GET all (empty)
        mockMvc.perform(get("/api/menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.menuItems").isEmpty());

        // 2. CREATE first item
        String request1 = """
                {
                  "name": "Pizza",
                  "description":"pizza pizza pizza",
                  "price": 20
                }
                """;

        MvcResult result1 = mockMvc.perform(post("/api/menu")
                        .contentType("application/json")
                        .content(request1))
                .andExpect(status().isCreated())
                .andReturn();
        String json = result1.getResponse().getContentAsString();
        CreateMenuItemResponse createdMenuItem = objectMapper.readValue(json, CreateMenuItemResponse.class);
        assertAll(
                () -> assertThat(createdMenuItem.name()).isEqualTo("Pizza"),
                () -> assertThat(createdMenuItem.description()).isEqualTo("pizza pizza pizza"),
                () -> assertThat(createdMenuItem.price()).isEqualTo("20"),
                () -> assertThat(createdMenuItem.id()).isEqualTo(1L)
        );

        Long id1 = createdMenuItem.id();

        // 2. CREATE second item
        String request2 = """
                {
                  "name": "Burger",
                  "description":"Burger Burger",
                  "price": 15
                }
                """;

        MvcResult result2 = mockMvc.perform(post("/api/menu")
                        .contentType("application/json")
                        .content(request2))
                .andExpect(status().isCreated())
                .andReturn();
        String json2 = result2.getResponse().getContentAsString();
        CreateMenuItemResponse createdMenuItem2 = objectMapper.readValue(json2, CreateMenuItemResponse.class);
        assertAll(
                () -> assertThat(createdMenuItem2.name()).isEqualTo("Burger"),
                () -> assertThat(createdMenuItem2.description()).isEqualTo("Burger Burger"),
                () -> assertThat(createdMenuItem2.price()).isEqualTo("15"),
                () -> assertThat(createdMenuItem2.id()).isEqualTo(2L)
        );

        Long id2 = createdMenuItem2.id();

        // 3. GET second item
        mockMvc.perform(get("/api/menu/" + id2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Burger"));

        // 4. GET all → 2 items
        mockMvc.perform(get("/api/menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.menuItems.length()").value(2));

        // 5. DELETE second
        mockMvc.perform(delete("/api/menu/" + id2))
                .andExpect(status().isNoContent());

        // 6. UPDATE first
        String updateRequest = """
                {
                  "name": "Pizza Updated",
                  "price": 25
                }
                """;

        mockMvc.perform(patch("/api/menu/" + id1)
                        .contentType("application/json")
                        .content(updateRequest))
                .andExpect(status().isOk());

        // 7. GET all → 1 items
        mockMvc.perform(get("/api/menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.menuItems.length()").value(1))
                .andExpect(jsonPath("$.menuItems[0].id").value(id1));

        //  8. VERIFY update
        mockMvc.perform(get("/api/menu/" + id1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza Updated"))
                .andExpect(jsonPath("$.price").value(25));
    }
}