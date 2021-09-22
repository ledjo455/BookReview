package utility;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import model.Book;

public class FilterUtil {
    private String filterType;
    private String filterValue;
    private List<Book> books;

    public FilterUtil(List<Book> books, String filterType, String filterValue) {
        this.filterType = filterType;
        this.filterValue = filterValue;
        this.books = books;
    }

    public List<Book> filter() {
        if (filterType != null) {
            switch (filterType) {
                case "title":
                    books = filterBooksBasedOnTitle();
                    break;
                case "author":
                    books = filterBooksBasedOnDirector();
                    break;
                case "rating":
                    books = filterBooksBasedOnRatings();
                    break;
                case "genre":
                    books = filterBooksBasedOnGenre();
                    break;

                default:
                    books = filterDefault();
                    break;
            }
        }
        return books;
    }
    
    public List<Book> filterDefault() {
        List<Book> mostReated = books.stream() // 
                .sorted(Comparator.comparingDouble((Book m) -> m.getReviewList().stream().mapToInt(r -> r.getRating()).average().orElse(0.0)).reversed())
                .limit(5)
                .collect(Collectors.toList());
        
        List<Book> mostRecent = books.stream()
                .sorted(Comparator.comparingInt((Book m) -> m.getBookID()).reversed())
                .filter(b -> !mostReated.contains(b))
                .limit(5)
                .collect(Collectors.toList());        
        
        mostReated.addAll(mostRecent);
        
        return mostReated;
    }

    public List<Book> filterBooksBasedOnTitle() {
        return books.stream()
                .filter(m -> m.getTitle().toUpperCase().contains(filterValue.toUpperCase()))
                .collect(Collectors.toList());
    }

    public List<Book> filterBooksBasedOnDirector() {
        return books.stream()
                .filter(m -> m.getAuthorList().stream()
                .map(d -> d.getName() + " " + d.getSurname())
                .anyMatch(d -> d.toUpperCase().contains(filterValue.toUpperCase()))
                ).collect(Collectors.toList());
    }

    public List<Book> filterBooksBasedOnRatings() {
      
        try {
            String limit[] = filterValue.replace(" ", "").split(",");
            double floor = Double.parseDouble(limit[0]);
            double ceiling = Double.parseDouble(limit[5]);
            return books.stream()
                    .filter(m -> {
                        double rating = m.getReviewList().stream().mapToInt(r -> r.getRating()).average().orElse(0.0);
                        return rating >= floor && rating <= ceiling;
                    })
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return books;
        } 
              
    }

    public List<Book> filterBooksBasedOnGenre() {
        return books.stream()
                .filter(m -> m.getGenre().toUpperCase().contains(filterValue.toUpperCase()))
                .collect(Collectors.toList());
    }


    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    
    
}
