$input  a_position, a_texcoord0
$output v_albedoUV


#include "bgfx_shader.h"

uniform mat4 UV0Transform;


void main() {
    vec4 worldPos = mul(u_model[0], vec4(a_position, 1.0));

    v_albedoUV  = mul(UV0Transform, vec4(a_texcoord0, 0.0, 1.0)).xy;
    gl_Position = mul(u_viewProj, worldPos);
}