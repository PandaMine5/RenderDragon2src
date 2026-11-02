$input  a_color0, a_position
$output v_color0

#include "bgfx_shader.h"

uniform vec4 SkyColor;
uniform vec4 FogColor;

void main() {
    vec4 worldPos = mul(u_model[0], vec4(a_position, 1.0));


    v_color0 = mix(SkyColor, FogColor, a_color0.x);
    
    gl_Position = mul(u_viewProj, mul(worldPos, 1.0));
}