#ifndef BGFX_FUNCTIONS_H_HEADER_GUARD
#define BGFX_FUNCTIONS_H_HEADER_GUARD


#if BGFX_SHADER_LANGUAGE_GLSL
    precision mediump float;
    precision highp   int;
#endif


#define vec2s(_x) vec2(_x, _x)
#define vec3s(_x) vec3(_x, _x, _x)
#define vec4s(_x) vec4(_x, _x, _x, _x)


#endif //