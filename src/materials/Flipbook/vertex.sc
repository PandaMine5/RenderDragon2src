$input  a_texcoord0, a_position
$output v_currFrame_uv, v_nextFrame_uv


#include "bgfx_shader.h"


uniform vec4 VBlendControl;


void main() {
    v_currFrame_uv = a_texcoord0;
    v_currFrame_uv.y += VBlendControl.x;
    
    v_nextFrame_uv = a_texcoord0;
    v_nextFrame_uv.y += VBlendControl.y;

    gl_Position = mul(u_modelViewProj, vec4(a_position, 1.0));
}