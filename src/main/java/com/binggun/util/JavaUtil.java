package com.binggun.util;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

public class JavaUtil {

    public static List<String> toList(String str,String replace){
        String[] args = str.split(replace);
        List<String> list = new ArrayList<>();
        for(String str2:args){
            list.add(str2);
        }
        return list;
    }

    public static String listToString(List<String> list,String replace){
        String str = "";
        for(String str2:list){
          str=str+str2+replace;
        }
        return str;
    }


    public static boolean isRunner(Player player, String formula) {
        formula = PlaceholderAPI.setPlaceholders(player, formula);

        formula = PlaceholderAPI.setPlaceholders(player, formula);
        if(formula.contains("==")){
            String[] papi = formula.split("==");
            if(papi[0].equalsIgnoreCase(papi[1])){
                return true;
            }else {
                return false;
            }
        }else if(formula.contains("!=")){
            String[] papi = formula.split("!=");
            if(papi[0].equalsIgnoreCase(papi[1])){
                return false;
            }else {
                return true;
            }
        }else {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            try {
                // 将表达式计算成布尔值
                Object result = engine.eval(formula);
                if (result instanceof Boolean) {
                    return (boolean) result;
                }
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public static double calculateFormula(Player player,String formula) throws Exception {
        formula = PlaceholderAPI.setPlaceholders(player, formula);
        ExpressRunner runner =new ExpressRunner();
        DefaultContext<String,Object>  Context= new DefaultContext<String, Object>();
        return Double.valueOf(runner.execute(formula,Context,null,true,false).toString());
    }


    public static int getIntRandom(int max, int min) {
        if(max==min){
            return min;
        }
        java.util.Random r=new java.util.Random();
        int n3  =r.nextInt(max-min);
        return  n3+min;
    }

}
