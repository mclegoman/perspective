#version 150

in vec2 texCoord;
out vec4 fragColor;

uniform sampler2D InSampler;

vec3 shiftHue(vec3 color, float shift) {
    vec3 P = vec3(0.55735)*dot(vec3(0.55735),color);
    vec3 U = color-P;
    vec3 V = cross(vec3(0.55735),U);
    color = U*cos(shift*6.2832) + V*sin(shift*6.2832) + P;
    return color;
}

void main() {
    vec4 color = texture(InSampler, texCoord);
    fragColor = vec4(shiftHue(1.0 - color.rgb, 0.5), color.a);
}