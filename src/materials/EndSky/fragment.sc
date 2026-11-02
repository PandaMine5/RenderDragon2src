$input v_albedoUV


#include "bgfx_shader.h"

SAMPLER2D_AUTOREG(s_SkyTexture);
uniform vec4 SkyColor;


void main() {
    vec4 endSky = texture2D(s_SkyTexture, v_albedoUV);

    gl_FragColor = SkyColor * endSky;
}