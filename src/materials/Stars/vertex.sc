$input  a_color0, a_position
$output v_star


#include "bgfx_shader.h"


void main() {
    vec4 worldPos = mul(u_model[0], vec4(a_position, 1.0));

    v_star = a_color0;
    gl_Position = mul(u_viewProj, worldPos);
}