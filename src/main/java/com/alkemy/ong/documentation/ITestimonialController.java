package com.alkemy.ong.documentation;

import com.alkemy.ong.dto.TestimonialBasicDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@Tag(name = "Testimonial", description = "CRUD of testimonials")
public interface ITestimonialController {

        @Operation(summary = "Get paginated testimonials" , description = "testimonial.get.page.description")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Resources fetched successfully",
                        content = {@Content(schema = @Schema(implementation = TestimonialDTO.class))}),
                @ApiResponse(responseCode = "400", description = "invalid.Page", content = @Content),
                @ApiResponse(responseCode = "403", description = "user.notAuthenticated", content = @Content),
                @ApiResponse(responseCode = "404", description = "page.NotFound", content = @Content),
                @ApiResponse(responseCode = "500", description = "unable.toFetch", content = @Content)
        })
        ResponseEntity<Map<String, Object>> getTestimonialPage(@Parameter(description = "Number of page")Integer page,
                                                               @Parameter(description = "Pageable object")Pageable pageable);

        @Operation(summary = "Create a new testimonial" , description = "testimonial.save.description")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Testimonial created successfully", content = @Content),
                @ApiResponse(responseCode = "403", description = "not.AdminRole", content = @Content),
                @ApiResponse(responseCode = "500", description = "unable.toSave", content = @Content)
        })
        ResponseEntity<?> save(@Parameter(description = "Testimonial DTO to save a new testimonial")
                               TestimonialDTO dto) throws Exception;

        @Operation(summary = "Update a Testimonial" , description = "testimonial.update.description")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Successfully updated",
                        content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = TestimonialDTO.class))}),
                @ApiResponse(responseCode = "403", description = "not.AdminRole", content = @Content),
                @ApiResponse(responseCode = "404", description = "testimonial.notFound", content = @Content),
                @ApiResponse(responseCode = "500", description = "unable.toUpdate", content = @Content)
        })
        ResponseEntity<TestimonialDTO> updateTestimonial(@Parameter(description = "Testimonial Id to update") Long id,
                                                         @Parameter(description = "Testimonial DTO to update testimonial data")
                                                         TestimonialBasicDTO dto);

        @Operation(summary = "Soft delete a testimonial" , description = "testimonial.delete.description")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "204", description = "Successfully deleted", content = @Content),
                @ApiResponse(responseCode = "403", description = "not.AdminRole", content = @Content),
                @ApiResponse(responseCode = "404", description = "testimonial.notFound", content = @Content),
                @ApiResponse(responseCode = "500", description = "unable.toDelete", content = @Content)
        })
        ResponseEntity<Void> delete(@Parameter(description = "Testimonial id to delete") Long id) throws ResourceNotFoundException;

}
