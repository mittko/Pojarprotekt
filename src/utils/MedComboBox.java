package utils;

public class MedComboBox extends EditableComboBox{

    @Override
    public String getDataPath() {
        return "files/med.txt";
    }

    @Override
    public boolean isEditable() {
        return true;
    }
}
