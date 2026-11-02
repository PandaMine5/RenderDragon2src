$input  a_position, a_color0, a_texcoord0, a_texcoord1
$output v_albedoUV, v_lightmapUV, v_color0, v_fog, v_clipPosition, v_worldPosition, v_ditheringAndMaskTinting

#include "bgfx_shader.h"
#include "func/actor.h"
#include "func/fog.h"
#include "func/msaa.h"

uniform vec4 FogAndDistanceControl;
uniform vec4 FogColor;
uniform vec4 MeshContext;
uniform vec4 RenderChunkFogAlpha;
uniform vec4 ViewPositionAndTime;


//  1-4 bit - sky light   (x)
//  4-8 bit - block light (y)
vec2 unpackLightmap(vec2 packed_uv) {
    float unpacked = round(packed_uv.y * 65535.0) * 0.0625; // 1/16

    float lm_block = fract(floor(unpacked) * 0.0625) * 1.06666667; // 0..15 → 0..1
    float lm_sky   = fract(unpacked) * 1.06666667;                 // 0..15 → 0..1

    return vec2(lm_block, lm_sky);
}

 
void main() {
    vec4 worldPos = mul(u_model[0], vec4(a_position, 1.0));
    float cameraDistance = length(ViewPositionAndTime.xyz - worldPos.xyz);

    
    vec4 fogControl = (MeshContext.x > 0.5) ? vec4(0.99, 1.0, 1E5, 1E5) : FogAndDistanceControl;
    float fogStart  = fogControl.x;
    float fogEnd    = fogControl.y;
    float renderDistance = fogControl.z;
    

    vec4 color0 = a_color0;
    uvec2 base16_lm = uvec2(round(a_texcoord1 * 65535.0));
    vec4 position = applyMSAAJitter(worldPos);


    #ifdef TRANSPARENT_PASS
        if (a_color0.a < 0.95) {
            color0.a = mix(color0.a, 1.0, clamp(cameraDistance / fogControl.w, 0.0, 1.0));
        }
    #endif


    v_color0 = color0;
    v_albedoUV = unpackAlbedo(a_texcoord0); 
    v_lightmapUV = unpackLightmap(a_texcoord1);

    v_fog = vec4(FogColor.rgb, applyFogFadeAlpha(cameraDistance, fogStart, fogEnd, renderDistance, RenderChunkFogAlpha.x));
    v_ditheringAndMaskTinting = vec2((base16_lm & 256u) != uvec2_splat(0u));
    v_clipPosition = position;
    v_worldPosition = vec4(worldPos.xyz, 1.0);

    gl_Position = position;
}