$input v_albedoUV

#include "bgfx_shader.h"


SAMPLER2D_AUTOREG(s_SunMoonTexture);
uniform vec4 SunMoonColor;


void main() {
    vec4 sun_n_moon = texture2D(s_SunMoonTexture, v_albedoUV);
    
    gl_FragColor = SunMoonColor * sun_n_moon;
}