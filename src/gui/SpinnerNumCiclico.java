package gui;

import javax.swing.SpinnerNumberModel;

public class SpinnerNumCiclico extends SpinnerNumberModel{
    private Number vMin;
    private Number vMax;

    public SpinnerNumCiclico(Number min, Number max){
        super();
        vMin = min;
        vMax = max;
        setValue(min);
        setStepSize(0.01);
    }

    @Override
    public Number getNextValue(){
        Number sv =(Number) super.getNextValue();
        if(sv.doubleValue()>vMax.doubleValue())
            return vMin;
        return sv;
    }

    @Override
    public Number getPreviousValue(){
        Number sv =(Number) super.getPreviousValue();
        if(sv.doubleValue()<vMin.doubleValue())
            return vMax;
        return sv;
    }

}
