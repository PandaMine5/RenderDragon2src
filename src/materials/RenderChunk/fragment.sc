$input v_albedoUV, v_lightmapUV, v_color0, v_fog, v_clipPosition, v_worldPosition, v_ditheringAndMaskTinting

#include "bgfx_shader.h"

SAMPLER2D_AUTOREG(s_MatTexture);
SAMPLER2D_AUTOREG(s_LightMapTexture);
SAMPLER2D_AUTOREG(s_SeasonsTexture);

uniform vec4 FogColor;
uniform vec4 DitherParams;
uniform vec4 DitherParams2[3];
uniform vec4 ViewPositionAndTime;

// Dithering not supported yet

void main() {
    vec4 albedo   = texture2D(s_MatTexture,      v_albedoUV);
    vec4 lightmap = texture2D(s_LightMapTexture, v_lightmapUV);
    vec4 seasons  = texture2D(s_SeasonsTexture,  v_color0.xy);

    #ifdef ALPHA_TEST_PASS
        if (albedo.a < 0.5) discard;
    #endif

    #ifdef SEASONS__ON
        albedo.rgb *= mix(vec3_splat(1.0), seasons.rgb * 2.0, v_color0.b);
        albedo.rgb *= v_color0.aaa;
    #else
        albedo.rgba *= v_color0.rgba;
    #endif

    albedo.rgb *= lightmap.rgb;
    albedo.rgb = mix(albedo.rgb, v_fog.rgb, v_fog.a);

    gl_FragColor = albedo;
}