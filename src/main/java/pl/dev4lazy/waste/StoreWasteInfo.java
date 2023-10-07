package pl.dev4lazy.waste;

public class StoreWasteInfo {

    private int id;
    private Integer analysisId;
    private Integer articleCode; // kod briko 6 cyfr
    private Integer storeId;
    private String articleName;
    private Double articleStorePrice;
    private Double articleRefPrice;
    private Double articleNewPrice;
    private Double articleNewMarginPercent;
    private Double articleLmPrice;
    private Double articleObiPrice;
    private Double articleCastoramaPrice;
    private Double articleLocalCompetitor1Price;
    private Double articleLocalCompetitor2Price;
    private String department;
    private String sector;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId( Integer analysisId) {
        this.analysisId = analysisId;
    }

    public Integer getArticleCode() {
        return articleCode;
    }

    public void setArticleCode( Integer articleCode) {
        this.articleCode = articleCode;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public Double getArticleStorePrice() {
        return articleStorePrice;
    }

    public void setArticleStorePrice(Double articleStorePrice) {
        this.articleStorePrice = articleStorePrice;
    }

    public Double getArticleRefPrice() {
        return articleRefPrice;
    }

    public void setArticleRefPrice(Double articleRefPrice) {
        this.articleRefPrice = articleRefPrice;
    }

    public Double getArticleNewPrice() {
        return articleNewPrice;
    }

    public void setArticleNewPrice(Double articleNewPrice) {
        this.articleNewPrice = articleNewPrice;
    }

    public Double getArticleNewMarginPercent() {
        return articleNewMarginPercent;
    }

    public void setArticleNewMarginPercent(Double articleNewMarginPercent) {
        this.articleNewMarginPercent = articleNewMarginPercent;
    }

    public Double getArticleLmPrice() {
        return articleLmPrice;
    }

    public void setArticleLmPrice(Double articleLmPrice) {
        this.articleLmPrice = articleLmPrice;
    }

    public Double getArticleObiPrice() {
        return articleObiPrice;
    }

    public void setArticleObiPrice(Double articleObiPrice) {
        this.articleObiPrice = articleObiPrice;
    }

    public Double getArticleCastoramaPrice() {
        return articleCastoramaPrice;
    }

    public void setArticleCastoramaPrice(Double articleCastoramaPrice) {
        this.articleCastoramaPrice = articleCastoramaPrice;
    }

    public Double getArticleLocalCompetitor1Price() {
        return articleLocalCompetitor1Price;
    }

    public void setArticleLocalCompetitor1Price(Double articleLocalCompetitor1Price) {
        this.articleLocalCompetitor1Price = articleLocalCompetitor1Price;
    }

    public Double getArticleLocalCompetitor2Price() {
        return articleLocalCompetitor2Price;
    }

    public void setArticleLocalCompetitor2Price(Double articleLocalCompetitor2Price) {
        this.articleLocalCompetitor2Price = articleLocalCompetitor2Price;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreWasteInfo)) return false;
        StoreWasteInfo that = (StoreWasteInfo) o;
        return id == that.id;
    }

    public static class StoreWasteInfoBuilder {

        private StoreWasteInfo storeWasteInfo = new StoreWasteInfo();

        /* TODO zrób zgodnie z polami StoreWasteInfo */
        public StoreWasteInfoBuilder analysisId( Integer id ) {
            storeWasteInfo.id = id;
            return this;
        }

        public StoreWasteInfoBuilder store(Integer store ){
            storeWasteInfo.storeId = store;
            return this;
        }

        public StoreWasteInfoBuilder articleCode(Integer articleCode ){
            storeWasteInfo.articleCode = articleCode;
            return this;
        }

        public StoreWasteInfoBuilder articleName(String articleName){
            storeWasteInfo.articleName = articleName;
            return this;
        }

        public StoreWasteInfoBuilder articleStorePrice(Double articleStorePrice ){
            storeWasteInfo.articleStorePrice = articleStorePrice;
            return this;
        }

        public StoreWasteInfoBuilder articleRefPrice(Double articleRefPrice ){
            storeWasteInfo.articleRefPrice = articleRefPrice;
            return this;
        }

        public StoreWasteInfoBuilder articleNewPrice(Double articleNewPrice ){
            storeWasteInfo.articleNewPrice = articleNewPrice;
            return this;
        }

        public StoreWasteInfoBuilder articleNewMarginPercent(Double articleNewMarginPercent ){
            storeWasteInfo.articleNewMarginPercent = articleNewMarginPercent;
            return this;
        }

        public StoreWasteInfoBuilder articleLmPrice(Double articleLmPrice ){
            storeWasteInfo.articleLmPrice = articleLmPrice;
            return this;
        }

        public StoreWasteInfoBuilder artlcleObiPrice(Double articleObiPrice ){
            storeWasteInfo.articleObiPrice = articleObiPrice;
            return this;
        }

        public StoreWasteInfoBuilder artlcleBricomanPrice(Double articleBricomanPrice ){
            storeWasteInfo.articleCastoramaPrice = articleBricomanPrice;
            return this;
        }

        public StoreWasteInfoBuilder articleLocalCompetitor1Price(Double articleLocalCompetitor1Price ){
            storeWasteInfo.articleLocalCompetitor1Price = articleLocalCompetitor1Price;
            return this;
        }

        public StoreWasteInfoBuilder articleLocalCompetitor2Price(Double articleLocalCompetitor2Price ){
            storeWasteInfo.articleLocalCompetitor2Price = articleLocalCompetitor2Price;
            return this;
        }

        public StoreWasteInfoBuilder department( String department ){
            storeWasteInfo.department = department;
            return this;
        }

        public StoreWasteInfoBuilder sector( String sector ){
            storeWasteInfo.sector = sector;
            return this;
        }

        public StoreWasteInfo build() {
            return storeWasteInfo;
        }
    }
    
}
