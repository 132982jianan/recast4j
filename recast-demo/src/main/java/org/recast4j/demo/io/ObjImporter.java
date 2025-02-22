/*
recast4j copyright (c) 2015-2019 Piotr Piastucki piotr@jtilia.org

This software is provided 'as-is', without any express or implied
warranty.  In no event will the authors be held liable for any damages
arising from the use of this software.
Permission is granted to anyone to use this software for any purpose,
including commercial applications, and to alter it and redistribute it
freely, subject to the following restrictions:
1. The origin of this software must not be misrepresented; you must not
 claim that you wrote the original software. If you use this software
 in a product, an acknowledgment in the product documentation would be
 appreciated but is not required.
2. Altered source versions must be plainly marked as such, and must not be
 misrepresented as being the original software.
3. This notice may not be removed or altered from any source distribution.
*/
package org.recast4j.demo.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Set;
import org.recast4j.demo.geom.DemoInputGeomProvider;

/**
 -------------
 o Wall_4x1_5_VarA_LOD1_3_Scene.015
 # Blender v2.83.5 OBJ File: ''
 s 1
 o Wall_Corner_1_5_VarB_LOD1_Scene.005
 o Main_10x10_LOD0_Scene.008
 o Wall_4x1_5_VarA_LOD1_5_Scene.017
 o Shutter_Small_LOD1_Scene.003
 o Wall_Corner_1_5_VarB_LOD1_1_Scene.010
 o Wall_4x1_5_VarA_LOD1_1_Scene.013
 o Shutter_Large_LOD1_3_Scene.022
 o Wall_4x1_5_VarA_LOD1_7_Scene.019
 o Shutter_Large_LOD1_1_Scene.002
 o Wall_Corner_1_5_VarB_LOD1_2_Scene.011
 o Wall_4x1_5_VarA_LOD1_4_Scene.016
 o Wall_4x1_5_VarA_LOD1_2_Scene.014
 o Roof_10x10_LOD1_Scene.009
 o Shutter_Large_LOD1_Scene.001
 o Door_LOD3_Scene
 o Floor_10x10_mdl_Scene.007
 o Door_LOD3_1_Scene.006
 o Shutter_Small_LOD1_1_Scene.020
 # www.blender.org
 o Wall_4x1_5_VarA_LOD1_6_Scene.018
 o Wall_4x1_5_VarA_LOD1_Scene.004
 o Shutter_Large_LOD1_2_Scene.021
 o Wall_Corner_1_5_VarB_LOD1_3_Scene.012
 -------------
 l 65 113
 l 54 102
 l 87 135
 l 37 12
 l 76 124
 l 88 136
 l 33 16
 l 29 20
 l 56 104
 l 55 103
 # Blender v2.83.5 OBJ File: 'convex.blend'
 l 66 114
 l 25 24
 l 77 125
 l 84 132
 l 38 11
 l 95 143
 l 52 100
 s off
 l 63 111
 o Cube
 l 34 15
 l 50 98
 l 85 133
 # www.blender.org
 l 74 122
 l 47 2
 l 43 6
 l 45 4
 l 41 8
 l 96 144
 l 53 101
 l 28 21
 l 30 19
 l 86 134
 l 64 112
 l 75 123
 l 90 138
 l 39 10
 l 92 140
 l 80 128
 l 51 99
 l 35 14
 l 91 139
 l 27 22
 l 70 118
 l 81 129
 l 31 18
 l 49 97
 l 60 108
 l 59 107
 l 62 110
 l 73 121
 l 57 105
 l 89 137
 l 61 109
 l 71 119
 l 36 13
 l 67 115
 l 94 142
 l 83 131
 l 72 120
 l 48 1
 l 44 5
 l 42 7
 l 40 9
 l 46 3
 l 78 126
 l 69 117
 l 32 17
 l 93 141
 l 58 106
 l 82 130
 l 26 23
 l 68 116
 l 79 127
 -------------
 g Generic
 o nav_test.obj
 mtllib nav_test.mtl
 usemtl Default
 g Cube
 */
public class ObjImporter {

    private Set<String> lineSet = new HashSet<>();

    private class ObjImporterContext {
        List<Float> vertexPositions = new ArrayList<>();
        List<Integer> meshFaces = new ArrayList<>();
    }

    /**
     * 这个应该是重要的方法，这个是根据obj文件导出自己的
     *
     * @param is
     * @return
     */
    public DemoInputGeomProvider load(InputStream is) {
        // 定点信息+三角形信息???
        ObjImporterContext context = new ObjImporterContext();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // 根据是v还是f
                readLine(line, context);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }


            System.out.println("-------------");
            for (String s : lineSet) {
                System.out.println(s);
            }

        }
        return new DemoInputGeomProvider(context.vertexPositions, context.meshFaces);

    }

    private void readLine(String line, ObjImporterContext context) {
        // 顶点???
        if (line.startsWith("v")) {
            readVertex(line, context);
        } else if (line.startsWith("f")) { // 面???
            readFace(line, context);
        }else{
            lineSet.add(line);
        }
    }

    private void readVertex(String line, ObjImporterContext context) {
        if (line.startsWith("v ")) {
            float[] vert = readVector3f(line);
            for (float vp : vert) {
                context.vertexPositions.add(vp);
            }
        }
    }

    private float[] readVector3f(String line) {
        String[] v = line.split("\\s+");
        if (v.length < 4) {
            throw new RuntimeException("Invalid vector, expected 3 coordinates, found " + (v.length - 1));
        }
        return new float[]{Float.parseFloat(v[1]), Float.parseFloat(v[2]), Float.parseFloat(v[3])};
    }

    private void readFace(String line, ObjImporterContext context) {
        String[] v = line.split("\\s+");
        if (v.length < 4) {
            throw new RuntimeException("Invalid number of face vertices: 3 coordinates expected, found " + v.length);
        }
        for (int j = 0; j < v.length - 3; j++) {
            context.meshFaces.add(readFaceVertex(v[1], context));
            for (int i = 0; i < 2; i++) {
                context.meshFaces.add(readFaceVertex(v[2 + j + i], context));
            }
        }
    }

    private int readFaceVertex(String face, ObjImporterContext context) {
        String[] v = face.split("/");
        return getIndex(Integer.parseInt(v[0]), context.vertexPositions.size());
    }

    private int getIndex(int posi, int size) {
        if (posi > 0) {
            posi--;
        } else if (posi < 0) {
            posi = size + posi;
        } else {
            throw new RuntimeException("0 vertex index");
        }
        return posi;
    }

}
