import com.sun.javadoc.*;
import java.util.regex.*;

public class ReuseTags {
    private static String patternString = 
        "\\{\\@reuse ([a-zA-Z0-9]+)#([a-zA-Z0-9]+)\\(([a-zA-Z]+(, [a-zA-Z]+)*)\\)(\\:([a-zA-Z0-9]+))*\\}";

    public static boolean start(RootDoc root){ 

        ClassDoc[] classes = root.classes();

        for (int i=0; i < classes.length; i++) {

            System.out.println("\n###################################\n");
            System.out.println("Class " + classes[i].name() + " : ");

            MethodDoc[] methods = classes[i].methods();

            for (int j=0; j < methods.length; j++) {

                Tag main = methods[j].inlineTags()[0];
                Tag[] tags = methods[j].tags();

                if (tags.length > 0) {
                    System.out.println(methods[j].name());

                    for (int k=0; k < tags.length; k++) {

                        Pattern pattern = Pattern.compile(patternString);
                        Matcher matcher = pattern.matcher(tags[k].text());

                        if(matcher.find()){
                            String tagId = tags[k].text().split(" ")[0];
                            String tagText = getTag(classes, 
                                                    matcher.group(1), 
                                                    matcher.group(2), 
                                                    "("+matcher.group(3).toLowerCase()+")", 
                                                    tags[k].name(), 
                                                    tagId, 
                                                    (matcher.group(6) == null)?"":matcher.group(6).toLowerCase());

                            System.out.println("   " + tags[k].name() + ": " 
                                + tags[k].text().replace(matcher.group(0), tagText));
                        }else{
                            System.out.println("   " + tags[k].name() + ": " + tags[k].text());
                        }
                    }
                } 
            }
        }

        return true;
    }


    private static String getTag(ClassDoc[] classes, String className, String methodName, 
                                    String params, String tag, String tagId, String tagRef) {

        String tagText = "";

        for (int i=0; i < classes.length; i++) {
            if(classes[i].name().equals(className)){

                MethodDoc[] methods = classes[i].methods();

                for (int j=0; j < methods.length; j++) {

                    if(methods[j].name().equals(methodName) && methods[j].flatSignature().equals(params)){

                        Tag[] tags = methods[j].tags();

                        for (int k=0; k < tags.length; k++) {
                            if(tags[k].name().equals(tag)) {
                                if(tag.equals("@param") || tag.equals("@throws") || tag.equals("@exception")){ 
                                    if(tagRef != null){
                                        if(tags[k].text().split(" ")[0].toLowerCase().equals(tagRef)){
                                            tagText = tags[k].text().replace(tags[k].text().split(" ")[0]+" ", "");
                                            break;
                                        }
                                    }else{
                                        if(tags[k].text().split(" ")[0].toLowerCase().equals(tagId)){
                                            tagText = tags[k].text().replace(tagId+" ", "");
                                            break;
                                        }
                                    }
                                }else{
                                    tagText = tags[k].text();
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }

        return tagText;
    }
}