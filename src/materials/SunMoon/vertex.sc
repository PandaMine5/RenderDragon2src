$input  a_texcoord0, a_position
$output v_albedoUV


#include "bgfx_shader.h"


void main() {
    vec4 worldPos = mul(u_model[0], vec4(a_position, 1.0));

    v_albedoUV  = a_texcoord0;
    gl_Position = mul(u_viewProj, worldPos);
}