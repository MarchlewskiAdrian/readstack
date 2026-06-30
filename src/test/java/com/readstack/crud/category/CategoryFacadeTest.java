package com.readstack.crud.category;

import com.readstack.crud.PageResponse;
import com.readstack.crud.discovery.DiscoveryLookup;
import com.readstack.crud.discovery.DiscoveryLookupImpl;
import com.readstack.dto.CategoryAddDto;
import com.readstack.dto.CategoryGetDto;
import com.readstack.validation.exception.CategoryContainsDiscoveriesException;
import com.readstack.validation.exception.ResourceExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryFacadeTest {

    private final InMemoryCategoryRepository inMemoryCategoryRepository = new InMemoryCategoryRepository();
    private final DiscoveryLookup discoveryLookup = mock(DiscoveryLookupImpl.class);


    private final CategoryFacade categoryFacade = createCategoryFacade(
            inMemoryCategoryRepository,
            discoveryLookup
            );

    private CategoryFacade createCategoryFacade(
            CategoryRepository categoryRepository,
            DiscoveryLookup discoveryLookup
    ) {
        CategoryDataValidator categoryDataValidator = new CategoryDataValidator(categoryRepository);
        CategoryBusinessValidator categoryBusinessValidator = new CategoryBusinessValidator(discoveryLookup);
        CategoryFetcher categoryFetcher = new CategoryFetcher(categoryRepository);
        CategoryAdder categoryAdder = new CategoryAdder(categoryRepository, categoryDataValidator);
        CategoryUpdater categoryUpdater = new CategoryUpdater(categoryFetcher, categoryRepository);
        CategoryDeleter categoryDeleter = new CategoryDeleter(categoryRepository, categoryDataValidator, categoryBusinessValidator);

        return new CategoryFacade(
                categoryFetcher,
                categoryUpdater,
                categoryDeleter,
                categoryAdder,
                discoveryLookup
        );

    }

    @Test
    void should_return_all_categories_when_categories_exist() {
        //given
        CategoryAddDto category1 = new CategoryAddDto("name1", "description1");
        CategoryAddDto category2 = new CategoryAddDto("name2", "description2");
        categoryFacade.add(category1);
        categoryFacade.add(category2);

        //when
        PageResponse<CategoryGetDto> all = categoryFacade.getAll(Pageable.unpaged());

        //then
        assertThat(all.items()).hasSize(2);
    }

    @Test
    void should_return_category_by_id_when_category_exists() {
        //given
        CategoryAddDto categoryAddDto = new CategoryAddDto("name1", "description1");
        CategoryGetDto addedCategory = categoryFacade.add(categoryAddDto);

        //when
        CategoryGetDto category = categoryFacade.getById(addedCategory.id());

        //then
        assertThat(category).isEqualTo(addedCategory);
    }

    @Test
    void should_update_category_by_id_when_category_exists() {
        //given
        CategoryAddDto category = new CategoryAddDto("name1", "description1");
        CategoryGetDto addedCategory = categoryFacade.add(category);

        //when
        CategoryAddDto updateCategory = new CategoryAddDto("name2", "description2");
        categoryFacade.updateById(addedCategory.id(), updateCategory);
        CategoryGetDto updatedCategory = categoryFacade.getById(addedCategory.id());

        //then
        assertThat(updatedCategory.id()).isEqualTo(addedCategory.id());
        assertThat(updatedCategory.name()).isEqualTo("name2");
        assertThat(updatedCategory.description()).isEqualTo("description2");

    }

    @Test
    void should_delete_category_by_id_when_has_no_discoveries() {
        //given
        CategoryAddDto categoryToAdd = new CategoryAddDto("name1", "description1");
        CategoryGetDto addedCategory = categoryFacade.add(categoryToAdd);

        //when
        when(discoveryLookup.existsByCategoryId(addedCategory.id())).thenReturn(false);
        categoryFacade.deleteById(addedCategory.id());

        //then
        PageResponse<CategoryGetDto> categories = categoryFacade.getAll(Pageable.unpaged());
        assertThat(categories.items()).isEmpty();
    }

    @Test
    void should_add_category() {
        //given
        CategoryAddDto categoryToAdd = new CategoryAddDto("name1", "description1");

        //when
        CategoryGetDto addedCategory = categoryFacade.add(categoryToAdd);

        //then
        CategoryGetDto category = categoryFacade.getById(addedCategory.id());
        assertThat(category.name()).isEqualTo("name1");
        assertThat(category.description()).isEqualTo("description1");
    }

    @Test
    void should_throw_exception_when_category_not_found_by_id() {
        //given

        //when & then
        assertThatThrownBy(() -> categoryFacade.getById(0L))
                .isInstanceOf(ResourceExistsException.class);
    }

    @Test
    void should_throw_exception_when_category_already_exists() {
        //given
        CategoryAddDto categoryToAdd = new CategoryAddDto("name1", "description1");
        CategoryAddDto categoryToAdd2 = new CategoryAddDto("name1", "description1");

        //when & then
        categoryFacade.add(categoryToAdd);

        assertThatThrownBy(() -> categoryFacade.add(categoryToAdd2))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessage("Category with name 'name1' already exists");

    }
    @Test
    void should_throw_exception_when_category_contains_discoveries(){
        //given
        CategoryAddDto categoryToAdd = new CategoryAddDto("name1", "description1");
        CategoryGetDto addedCategory = categoryFacade.add(categoryToAdd);

        //when
        when(discoveryLookup.existsByCategoryId(addedCategory.id())).thenReturn(true);

        //then
        assertThatThrownBy(() -> categoryFacade.deleteById(addedCategory.id()))
                .isInstanceOf(CategoryContainsDiscoveriesException.class)
                .hasMessage( "Category with id " + addedCategory.id() + " contains discoveries");
    }

}
