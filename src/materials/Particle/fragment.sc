$input v_albedoUV, v_color0, v_fog


#include "bgfx_shader.h"

SAMPLER2D_AUTOREG(s_ParticleTexture);


void main() {
    vec4 albedo = texture2D(s_ParticleTexture, v_albedoUV);

    #ifdef ALPHA_TEST_PASS
        if (albedo.a < 0.5) discard;
    #endif

    // Potion tint & lightmap
    albedo.rgb *= v_color0.rgb;
    albedo.rgb = mix(albedo.rgb, v_fog.rgb, v_fog.a);    
    
    gl_FragColor = albedo;
}