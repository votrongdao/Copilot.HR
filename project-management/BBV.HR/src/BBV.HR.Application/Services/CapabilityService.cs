using BBV.HR.Application.DTOs.Capability;
using BBV.HR.Application.Interfaces.Repositories;
using BBV.HR.Application.Interfaces.Services;
using FluentValidation;
using BBV.HR.Application.Mappings;
using System;
using System.Collections.Generic;
using System.Text;
using BBV.HR.Application.Entities;

namespace BBV.HR.Application.Services
{
    public class CapabilityService : ICapabilityService
    {
        private readonly ICapabilityRepository _capabilityRepository;
        private readonly IValidator<CreateCapabilityDto> _createValidator;
        private readonly IValidator<UpdateCapabilityDto> _updateValidator;

        public CapabilityService(
            ICapabilityRepository capabilityRepository,
            IValidator<CreateCapabilityDto> createValidator,
            IValidator<UpdateCapabilityDto> updateValidator)
        {
            _capabilityRepository = capabilityRepository;
            _createValidator = createValidator;
            _updateValidator = updateValidator;
        }

        public async Task<CapabilityDto> CreateCapabilityAsync(CreateCapabilityDto dto)
        {
            await _createValidator.ValidateAndThrowAsync(dto);

            var exists = await _capabilityRepository.ExistsNameAsync(dto.Name);
            if(exists)
            {
                throw new ArgumentException($"Capability with name '{dto.Name}' already exists.");
            }

            var capability = new Capability
            {
                Id = Guid.NewGuid(),
                Name = dto.Name,
                Category = dto.Category,
                Description = dto.Description,
                CreatedAt = DateTime.UtcNow,
                UpdatedAt = DateTime.UtcNow
            };

            var created = await _capabilityRepository.AddAsync(capability);
            return created.ToDto();
        }

        public async Task<bool> DeleteCapabilityAsync(Guid id)
        {
            var capability = await _capabilityRepository.GetByIdAsync(id);
            if (capability == null) return false;

            var inUse = await _capabilityRepository.IsInUseAsync(id);
            if (inUse)
            {
                throw new InvalidOperationException("Cannot delete capability as it is currently in use by employees or projects.");
            }

            await _capabilityRepository.DeleteAsync(capability);
            return true;
        }

        public async Task<IEnumerable<CapabilityDto>> GetAllCapabilitiesAsync(string? search, string? category)
        {
            var capabilities = await _capabilityRepository.GetAllAsync(search, category);

            return capabilities.Select(c => c.ToDto());
        }

        public async Task<CapabilityDto?> GetCapabilityByIdAsync(Guid id)
        {
            var capability = await _capabilityRepository.GetByIdAsync(id);
            return capability?.ToDto();
        }

        public async Task<CapabilityDto?> UpdateCapabilityAsync(Guid id, UpdateCapabilityDto dto)
        {
            await _updateValidator.ValidateAndThrowAsync(dto);

            var capability = await _capabilityRepository.GetByIdAsync(id);
            if (capability == null) return null;

            if(!string.IsNullOrWhiteSpace(dto.Name) && !dto.Name.Equals(capability.Name, StringComparison.OrdinalIgnoreCase))
            {
                var exists = await _capabilityRepository.ExistsNameAsync(dto.Name, id);
                if (exists)
                {
                    throw new InvalidOperationException($"Capability with name '{dto.Name}' already exists.");
                }
                capability.Name = dto.Name;
            }

            if (dto.Description != null) capability.Description = dto.Description;
            if (dto.Category != null) capability.Category = dto.Category;

            await _capabilityRepository.UpdateAsync(capability);
            return capability.ToDto();
        }
    }
}
