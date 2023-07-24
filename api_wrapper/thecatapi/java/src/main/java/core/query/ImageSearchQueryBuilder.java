package core.query;

import java.util.List;
import java.util.ArrayList;

public class ImageSearchQueryBuilder {
    private ImageSize size = ImageSize.MED;
    private final List<MimeType> mimeTypes = new ArrayList<>();
    private Format format = Format.JSON;
    private Order order = Order.RANDOM;
    private Integer page;
    private Integer limit = 1;
    private final List<String> categoryIDs = new ArrayList<>();
    private final List<String> breedIDs = new ArrayList<>();
    private boolean hasBreeds = false;

    public void setSize(ImageSize size) {
        this.size = size;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setPage(int page) {
        if (order == Order.RANDOM)
            throw new IllegalArgumentException("You can only use page if order is set to 'ASC' or 'DESC'!");

        this.page = page;
    }

    public void setLimit(int limit) {
        // Maximum = 25
        if (limit > 25)
            throw new IllegalArgumentException("Limit cannot be greater than 25!");

        this.limit = limit;
    }

    public void setHasBreeds(boolean hasBreeds) {
        this.hasBreeds = hasBreeds;
    }

    public void addMimeType(MimeType... type) {
        mimeTypes.addAll(List.of(type));
    }

    public void addCategoryID(String... id) {
        categoryIDs.addAll(List.of(id));
    }

    public void addBreedID(String... id) {
        breedIDs.addAll(List.of(id));
    }

    public String build() {
        List<String> builder = new ArrayList<>();

        builder.add(size.query());
        if (mimeTypes.size() == 0) {
            builder.add("mime_types=jpg,gif,png");
        } else {
            List<String> types = new ArrayList<>();
            for (MimeType m : mimeTypes) {
                types.add(m.toString());
            }
            builder.add(String.format("mime_types=%s", String.join(",", types)));
        }

        builder.add(format.query());
        builder.add(order.query());
        builder.add(String.format("limit=%s", limit));

        if (page != null)
            builder.add(String.format("page=%s", page));

        if (categoryIDs.size() > 0)
            builder.add(String.format("category_ids=%s", String.join(",", categoryIDs)));

        if (breedIDs.size() > 0)
            builder.add(String.format("breed_ids=%s", String.join(",", breedIDs)));

        builder.add(hasBreeds ? "has_breeds=1" : "has_breeds=0");

        return String.join("&", builder);
    }
}
