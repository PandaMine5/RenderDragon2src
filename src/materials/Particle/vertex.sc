$input  a_texcoord0, a_color0, a_position
$output v_albedoUV, v_color0, v_fog


#include "bgfx_shader.h"
#include "func/fog.h"


uniform vec4 FogAndDistanceControl;
uniform vec4 FogColor;


void main() {
    vec4 worldPos = mul(u_model[0], vec4(a_position, 1.0));


    float cameraDistance = a_position.z;
    float renderDistance = FogAndDistanceControl.z;
    float fogStart = FogAndDistanceControl.x;
    float fogEnd   = FogAndDistanceControl.y;


    v_color0 = a_color0;
    v_fog = vec4(FogColor.rgb, applyFogFade(cameraDistance, fogStart, fogEnd, renderDistance));
    v_albedoUV = a_texcoord0;


    gl_Position = mul(u_viewProj, vec4(worldPos.xyz, 1.0));
}