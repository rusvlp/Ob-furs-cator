package org.example;

import java.util.*;

import static org.example.RandomGeneration.createRandomString;

public class FunctionProcessor {

    //static List<Function> functions;
    public static List<String> unreachableCodePool = new ArrayList<>(){
        {
            List<String> ids = new ArrayList<>(){
                {
                    this.add(createRandomString());
                    this.add(createRandomString());
                    this.add(createRandomString());
                }
            };
            this.add("int " + ids.get(0) + " = 0;"  + "if ("+ ids.get(0) +" == 1){for (int i = 0; i<228; i++){}}");
            this.add("for (int i = 0; i<1337; i++){printf(\"hello world \")}");
        }
    };


    public static List<Function> parseFunctionList(String src){
        List<Function> functions = new ArrayList<>();

        Map<String, Integer> dataTypesCompletion = new HashMap<>();

        for(DataTypes dt: DataTypes.values()){
            dataTypesCompletion.put(dt.value, 0);
            // dataTypesCompletion.put(dt.value + "\\*".replaceAll("", ""), 0);
        }


        //Array navigation mode switchers
        boolean detectingDT = true;
        boolean detectingName = false;
        boolean detectingArgs = false;
        boolean parseBody = false;

        //Temp Function
        Function currentFun = new Function();

        //LinkedLists as bracket stacks
        Deque<Character> openStack = new LinkedList<>();
        Deque<Character> closeStack = new LinkedList<>();


        for (int i = 0; i<src.length(); i++){
            if (detectingDT){
                for (String key: dataTypesCompletion.keySet()){
                    if (key.charAt(dataTypesCompletion.get(key)) == src.charAt(i)){
                        dataTypesCompletion.put(key, dataTypesCompletion.get(key)+1);
                    }
                    if (dataTypesCompletion.get(key) == key.length()){
                        dataTypesCompletion.put(key, 0);
                        if (src.charAt(i+1) == '*'){
                            key += "*";
                        }


                        currentFun.dataType = key;
                        detectingDT = false;
                        detectingName = true;
                        break;
                    }
                }
            }
            if (detectingName){
                if (src.charAt(i) != '('){
                    currentFun.name += src.charAt(i);
                } else {
                    currentFun.name = currentFun.name.split(" ")[1];
                    detectingName = false;
                    detectingArgs = true;
                }

            }

            if (detectingArgs){
                String argsString = src.substring(i).split("\\(")[1].split("\\)")[0];
                i += argsString.length()+2;

                detectingArgs = false;
                parseBody = true;
                currentFun.args += argsString;
            }

            if (parseBody){
                currentFun.body += src.charAt(i);
                boolean continueFlag = false;
                if (src.charAt(i) == '{'){
                    openStack.push('{');
                }
                if (src.charAt(i) == '}'){

                    closeStack.push('}');
                    Deque<Character> tmpOpen = new LinkedList<>(openStack);
                    Deque<Character> tmpClose = new LinkedList<>(closeStack);
                    if (tmpOpen.size() != tmpClose.size()){
                        continueFlag = true;
                        continue;
                    }
                    while (tmpClose.size() < tmpOpen.size() ? tmpClose.size() != 0 : tmpOpen.size() != 0){
                        if (tmpOpen.pop() != tmpClose.pop() ){

                            continueFlag = true;

                        }
                    }
                }
                if (continueFlag){
                    openStack = new LinkedList<>();
                    closeStack = new LinkedList<>();
                    parseBody = false;
                    detectingDT = true;
                    functions.add(currentFun);
                    currentFun = new Function();
                }
            }



        }
        /*for (Function f: functions){
            System.out.println(f);
        }*/

        return functions;
    }

    public static Map<Function, Integer> findUsages(List<Function> functions){
        Map<Function, Integer> functionUsagesCount = new HashMap<>();

        for (Function f: functions){
            functionUsagesCount.put(f, 1);
        }

        for (Function f: functions){
            for (Function usage: functions){
                if (usage.name.equals(f.name)){
                    continue;
                }
                boolean detectingUsage = true;
                int namePosition = 0;
                for (int i = 0; i < f.body.length(); i++){
                    if (detectingUsage){
                        if (f.body.charAt(i) == usage.name.charAt(namePosition)){
                            namePosition++;
                        }
                        if (namePosition == usage.name.length()){
                            namePosition = 0;
                            if (f.body.charAt(i+1) == '('){
                                f.body = new StringBuffer(f.body).insert(i+1, functionUsagesCount.get(usage) + "").toString();
                                functionUsagesCount.put(usage, functionUsagesCount.get(usage)+1);
                            }


                        }
                    }
                }
            }
        }
        /*for (Function f: functions){
            System.out.println(f);
        }*/
        //System.out.println("______________________");
        return functionUsagesCount;
    }

    public static List<Function> addFunctionClones(String src){
        List<Function> resultFunctions = new ArrayList<>();
        List<Function> functions = parseFunctionList(src);
        Map<Function, Integer> functionUsageCount = findUsages(functions);

        for (Function f: functions){

            if (functionUsageCount.get(f) > 1){
                functionUsageCount.put(f, functionUsageCount.get(f)-1);
            }
            for (int i = 0; i<functionUsageCount.get(f); i++){
                Function currenFunction = new Function(f);

                if (f.name.equals("main")){
                    currenFunction.name = "main";
                    currenFunction.obfuscatedName = "main";
                } else {
                    currenFunction.name += i+1 + "";
                    currenFunction.obfuscatedName = createRandomString();
                }
                resultFunctions.add(currenFunction);
            }
        }
        /*for (Function f: resultFunctions){
            System.out.println(f);
        }*/
        return resultFunctions;
    }

    public static List<Function> addUnreachableCode(List<Function> src){
        int placeToAdd = -1;
        String returnSt = "return";
        int returnCount = 0;
        boolean detectingReturn = false;
        for (Function f: src){
           for (int i =0; i< f.body.length(); i++){
               if (!detectingReturn && f.body.charAt(i) == '{'){
                   detectingReturn = true;
                   placeToAdd = i+1;
                   break;
               }
           }
           f.body = new StringBuilder(f.body).insert(placeToAdd, unreachableCodePool.get(0)).toString();
        }
        return src;
    }

    public static String processFunctions(String src){
        String result = "";
        List<Function> functions = addFunctionClones(src);
       functions = addUnreachableCode(functions);
        for (Function f: functions){
            //System.out.println(f);
            result += f.dataType + " " + f.name + "(" + f.args + ")" + f.body;
        }
        for (Function f: functions){
            result = result.replaceAll(f.name + "\\(", f.obfuscatedName + "\\(");
            result = result.replaceAll("\t", "");
            result = result.replaceAll("\n", "");
        }
        //System.out.println(result);
        //System.out.println(result);
      //  System.out.println(src.split(">")[src.split((">")).length-1]);
       // System.out.println(src);
        return result;
    }


}
