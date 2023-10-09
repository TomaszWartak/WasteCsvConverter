package pl.dev4lazy.waste.model;

public class WasteCodeInfo {
    private String store;
    private String name;
    private String region;

    private String wasteCode;
    private Double amountOfWaste;

    public String getStore() {
        return store;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public void setWasteCode(String wasteCode) {
        this.wasteCode = wasteCode;
    }

    public String getWasteCode() {
        return wasteCode;
    }

    public void setAmountOfWaste(Double amountOfWaste) {
        this.amountOfWaste = amountOfWaste;
    }

    public Double getAmountOfWaste() {
        return amountOfWaste;
    }

    public static class Builder {
        private String store;
        private String name;
        private String region;
        private String wasteCode;
        private Double amountOfWaste;

        public Builder withStore(String store) {
            this.store = store;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder withWasteCode(String wasteCode) {
            this.wasteCode = wasteCode;
            return this;
        }

        public Builder withAmountOfWaste(Double amountOfWaste) {
            this.amountOfWaste = amountOfWaste;
            return this;
        }

        public WasteCodeInfo build() {
            WasteCodeInfo wasteCodeInfo = new WasteCodeInfo();
            wasteCodeInfo.store = this.store;
            wasteCodeInfo.name = this.name;
            wasteCodeInfo.region = this.region;
            wasteCodeInfo.wasteCode = this.wasteCode;
            wasteCodeInfo.amountOfWaste = this.amountOfWaste;
            return wasteCodeInfo;
        }
    }
}
