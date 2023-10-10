package pl.dev4lazy.waste.model;

import pl.dev4lazy.waste.interfaces.Coder;
import pl.dev4lazy.waste.model.WasteCodeInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class WasteCodeInfoToCsvLineCoder implements Coder< WasteCodeInfo, ArrayList<String> > {

    @Override
    public ArrayList<String> code(WasteCodeInfo wasteCodeInfo) {
        ArrayList<String> elements = new ArrayList<>();
        elements.add( wasteCodeInfo.getStore() );
        elements.add( wasteCodeInfo.getName() );
        elements.add( wasteCodeInfo.getRegion() );
        elements.add( wasteCodeInfo.getWasteCode() );
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        elements.add( decimalFormat.format(wasteCodeInfo.getAmountOfWaste()) );
        return elements;
    }
}
