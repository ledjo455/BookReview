
package beans;

import dao.DaoAuthor;
import dao.DaoBook;
import dao.DaoHouse;
import dao.DaoReview;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Author;
import model.Book;
import model.House;
import source.PagesController;
import utility.FilterUtil;

@Named("bookBean")
@SessionScoped
public class BookBean implements Serializable {
    @Inject
    private AuthenticationBean authentication;
    private List<Book> books;
    private Book selectedBooks = null;
    
    private DaoReview daoReview = new DaoReview();
    private DaoAuthor daoAuthor = new DaoAuthor();

    private DaoBook daoBook = new DaoBook();
    private DaoHouse daoHouse = new DaoHouse();
    
    private String filterType = "none";
    private String filterValue = "";
    
    private String title = "";
    private String author = "";
    private String genre = "";
    private String rating = "";
    private boolean filtering = false;
    private int countBooks = 0;
    
    private String nameHouse = "";
    private String nameAuthor = "";

    
    public BookBean() {
    }

    @PostConstruct
    public void init() {
        loadList();
    }

    public AuthenticationBean getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AuthenticationBean authentication) {
        this.authentication = authentication;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    
    public boolean hasBooks() {
        return !books.isEmpty();
    }

    public String getNameHouse() {
        return nameHouse;
    }

    public void setNameHouse(String nameHouse) {
        this.nameHouse = nameHouse;
    }

    public String getTitle() {
        return title;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }
    
    

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return author;
    }
    
    public void startCount(){
        countBooks = 0;
    }
    
    public boolean isSeparator(){
        
        System.out.println(filterType);
        System.out.println(filterType.equals("none") && countBooks == 1);
        return filterType.equals("none") && (++countBooks) == 1;
    }

    public void setDirector(String director) {
        this.author = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }
    
    public boolean validateRating(String s){
        
        if(s.length() < 2 || !s.contains(",")) return false;
        
        String[] parts = s.split(",", 2);
        
        if(parts.length != 2) return false;
        
        try {
            Integer.parseInt(parts[0]);
            Integer.parseInt(parts[1]);
        }catch(NumberFormatException e){
            return false;
        }
        
        return true;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
    
    
    

    public Book getSelectedBook() {
        return selectedBooks;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBooks = selectedBook;
    }

    public String getFilterType() {
        return filterType;
    }
    
    
    
    public boolean isFilterTypeAll(){
        
        return filterType.equals("all");
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }
    
    public Double getAverageScore() {
        return getAverageScore(selectedBooks);
    }
    
    public Double getAverageScore(Book book) {
        return book.getReviewList().stream()
                .mapToInt(r -> r.getRating())
                .average()
                .orElse(0);
    }
        
    public void loadList() {
        if(!filtering){
            books = daoBook.findAll();        
            filter();
        }
        
        filtering = true;
      
    }
    
    public void reloadSelectedBook() {
        selectedBooks = daoBook.find(selectedBooks.getBookID());        
    }
    
    public String actionReload() {
        loadList();
        return PagesController.getInstance().redirectToIndex();
    }
    
    public String prepareView(Book book) {
        if (book != null){
            selectedBooks = book;
            selectedBooks.setReviewList(daoReview.findAll(selectedBooks.getBookID()));
        }

        return PagesController.getInstance().redirectToBookView();
    }

    public String prepareCreate() {
        if (!authentication.hasCurrentUser())
            return PagesController.getInstance().redirectToLogin();
        selectedBooks = new Book();
        return PagesController.getInstance().redirectToBookCreate();
    }
    
    public String create() {
        try {
            if (!authentication.hasCurrentUser()) {
                return PagesController.getInstance().redirectToLogin();
            }
            
            House h = daoHouse.find(nameHouse);
            
            
            if(h == null) {
                h = new House(nameHouse);
                daoHouse.create(h);
                h = daoHouse.find(nameHouse);
            }
                        
            nameHouse = "";
            
            Author a = daoAuthor.find(nameAuthor);
            
            if(a == null) {
                a = new Author(nameAuthor);
                daoAuthor.create(a);
                a = daoAuthor.find(nameAuthor);
            }
                        
            nameAuthor = "";
            
            if(selectedBooks.getAuthorList() != null)
                selectedBooks.getAuthorList().add(a);
            else
                selectedBooks.setAuthorList(Arrays.asList(a));
                    
            selectedBooks.setHouse(h);
            selectedBooks.setRating("R");
            daoBook.create(selectedBooks);

               System.out.println("Paso bien");
            loadList();
            return PagesController.getInstance().redirectToBookView();
        } catch (Exception e) {
            e.printStackTrace();
            loadList();
            return null;
        }     
    }
    
    public String prepareEdit(Book book) {
        if (!authentication.hasCurrentUser())
            return PagesController.getInstance().redirectToLogin();
        if (book != null)
            selectedBooks = book;
        return PagesController.getInstance().redirectToBookEdit();
    }
    
    public String update() {
        try {
            if (!authentication.hasCurrentUser()) {
                return PagesController.getInstance().redirectToLogin();
            }
            daoBook.update(selectedBooks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadList();
        return PagesController.getInstance().redirectToBookView();
    }
    
    public String delete(Book book) {
        if (book != null)
            selectedBooks = book;
                
        try {
            if (!authentication.hasCurrentUser()) {
                return PagesController.getInstance().redirectToLogin();
            }
            daoBook.delete(selectedBooks);
        } catch (Exception e) {
            e.printStackTrace();
            return PagesController.getInstance().redirectToBookView();
        }
        
        return actionReload();
    }
    
    public String filterAndLoad(){
        books = daoBook.findAll();
        filterType = "none";
        filter();
        
        title = title.trim().toLowerCase();
        author = author.trim().toLowerCase();
        genre = genre.trim().toLowerCase();
        rating = rating.trim().toLowerCase();
        
        System.out.println("BookBean{" + "title=" + title + ", director=" + author + ", genre=" + genre + ", rating=" + rating + '}');
        
        
        FilterUtil filter = new FilterUtil(books, "title", title);
        
        if(title.length() != 0)
            books = filter.filter();
        
        if(genre.length() != 0){
            filter.setFilterType("genre");
            filter.setFilterValue(genre);
            books = filter.filter();
        }
        
        if(author.length() != 0){
            filter.setFilterType("author");
            filter.setFilterValue(author);
            books = filter.filter();
        }
        
        if(validateRating(rating)){
            filter.setFilterType("rating");
            filter.setFilterValue(rating);
            books = filter.filter();
        }
        
        System.out.println(books.size());        
        
        filtering = true;
        
        return PagesController.getInstance().redirectToIndex();
    }
    
    
    public void filter() {
        books = new FilterUtil(books, filterType, filterValue).filter();
    }

    
    
}
