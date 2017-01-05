package org.nearbyshops.gidb.ModelStats;

/**
 * Created by sumeet on 26/5/16.
 */
public class ItemStats {

    private Integer itemID;
    private Double min_price;
    private Double max_price;
    private Double avg_price;
    private Integer shopCount;

    private Double rating_avg;
    private Integer ratingCount;


    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public Double getMin_price() {
        return min_price;
    }

    public void setMin_price(Double min_price) {
        this.min_price = min_price;
    }

    public Double getMax_price() {
        return max_price;
    }

    public void setMax_price(Double max_price) {
        this.max_price = max_price;
    }

    public Double getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(Double avg_price) {
        this.avg_price = avg_price;
    }

    public Integer getShopCount() {
        return shopCount;
    }

    public void setShopCount(Integer shopCount) {
        this.shopCount = shopCount;
    }

    public Double getRating_avg() {
        return rating_avg;
    }

    public void setRating_avg(Double rating_avg) {
        this.rating_avg = rating_avg;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }
}
