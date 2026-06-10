function [V_out,M_out, v_out] = Mass_Volume_Spesific_Volume(Volume,Mass,Spesific_Volume)
%MASS_VOLUME_SPESIFIC_VOLUME Summary of this function goes here
%   Detailed explanation goes here
V_out = Volume;
M_out = Mass;
v_out = Spesific_Volume;

if(~isempty(Volume) && ~isempty(Mass))
    v_out = Volume/Mass;
end
if(~isempty(Volume) && ~isempty(Spesific_Volume))
    M_out = Volume/Spesific_Volume;
end
if(~isempty(Spesific_Volume) && ~isempty(Mass))
    V_out = Spesific_Volume*Mass;
end

end

