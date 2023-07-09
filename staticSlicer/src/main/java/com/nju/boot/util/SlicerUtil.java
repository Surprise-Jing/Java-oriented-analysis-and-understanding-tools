package com.nju.boot.util;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

public class SlicerUtil {
    public static CallableDeclaration<?>  findMethodByLineNumber(CompilationUnit cu,int lineNumber){

        for (TypeDeclaration<?> typeDeclaration:cu.getTypes()){
            for (BodyDeclaration<?> bodyDeclaration:typeDeclaration.getMembers()){
                if(bodyDeclaration instanceof CallableDeclaration<?>){
                    Range rangeOfBody = bodyDeclaration.getRange().get();
                    if(rangeOfBody.begin.line<=lineNumber&&rangeOfBody.end.line>=lineNumber)
                        return(CallableDeclaration<?>) bodyDeclaration;
                }
            }
        }
        return null;
    }
}
