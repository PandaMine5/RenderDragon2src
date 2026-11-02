$input v_star


#include "bgfx_shader.h"

uniform vec4 StarsColor;


void main() {
    vec3 tint = v_star.rgb * StarsColor.rgb;
    
    // Premultiply alpha
    gl_FragColor = vec4(tint * v_star.a, v_star.a);
}