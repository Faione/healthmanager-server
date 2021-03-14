package stu.swufe.healthmanager.common;

public enum ImagType {
    PNG("png", "image/png"),
    JPG("jpg", "image/jpg"),
    GIF("gif", "image/gif")

    ;


    private  String type;
    private  String typeInBody;

    ImagType(String type, String typeInBody){
        this.type = type;
        this.typeInBody = typeInBody;
    }

    public static ImagType fromContentTypeToImageType(String contentType){
        for(ImagType imagType : ImagType.values()){
            if(contentType.equals(imagType.getTypeInBody())) return imagType;
        }

        return null;
    }

    public static ImagType fromIndexToImageType(int index){
        if(index>=ImagType.values().length) return null;

        return ImagType.values()[index];
    }



    public String getType() {
        return type;
    }


    public String getTypeInBody() {
        return typeInBody;
    }

}
