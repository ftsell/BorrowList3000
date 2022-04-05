/* tslint:disable */
/* eslint-disable */
/**
 * Thing-People-List Api
 * Public API of the Thing-People-List Service. Thing-People-List is a Web-Service to keep track of which people did what i.e. who borrowed what from you.
 *
 * The version of the OpenAPI document: 2.0.0
 * Contact: dev@finn-thorben.me
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import * as runtime from "../runtime";
import type { ChangeUserDto, Problem, UserDto } from "../models";

import {
  ChangeUserDtoFromJSON,
  ChangeUserDtoToJSON,
  ProblemFromJSON,
  ProblemToJSON,
  UserDtoFromJSON,
  UserDtoToJSON,
} from "../models";

export interface PatchMeRequest {
  changeUserDto: ChangeUserDto;
}

/**
 *
 */
export class UserControllerApi extends runtime.BaseAPI {
  /**
   * Retrieve the currently logged in user
   */
  async getMeRaw(
    initOverrides?: RequestInit
  ): Promise<runtime.ApiResponse<UserDto>> {
    const queryParameters: any = {};

    const headerParameters: runtime.HTTPHeaders = {};

    if (this.configuration && this.configuration.apiKey) {
      headerParameters["Authorization"] =
        this.configuration.apiKey("Authorization"); // token authentication
    }

    const response = await this.request(
      {
        path: `/api/users/me`,
        method: "GET",
        headers: headerParameters,
        query: queryParameters,
      },
      initOverrides
    );

    return new runtime.JSONApiResponse(response, (jsonValue) =>
      UserDtoFromJSON(jsonValue)
    );
  }

  /**
   * Retrieve the currently logged in user
   */
  async getMe(initOverrides?: RequestInit): Promise<UserDto> {
    const response = await this.getMeRaw(initOverrides);
    return await response.value();
  }

  /**
   * Update information of the currently logged in user
   */
  async patchMeRaw(
    requestParameters: PatchMeRequest,
    initOverrides?: RequestInit
  ): Promise<runtime.ApiResponse<UserDto>> {
    if (
      requestParameters.changeUserDto === null ||
      requestParameters.changeUserDto === undefined
    ) {
      throw new runtime.RequiredError(
        "changeUserDto",
        "Required parameter requestParameters.changeUserDto was null or undefined when calling patchMe."
      );
    }

    const queryParameters: any = {};

    const headerParameters: runtime.HTTPHeaders = {};

    headerParameters["Content-Type"] = "application/json";

    if (this.configuration && this.configuration.apiKey) {
      headerParameters["Authorization"] =
        this.configuration.apiKey("Authorization"); // token authentication
    }

    const response = await this.request(
      {
        path: `/api/users/me`,
        method: "PATCH",
        headers: headerParameters,
        query: queryParameters,
        body: ChangeUserDtoToJSON(requestParameters.changeUserDto),
      },
      initOverrides
    );

    return new runtime.JSONApiResponse(response, (jsonValue) =>
      UserDtoFromJSON(jsonValue)
    );
  }

  /**
   * Update information of the currently logged in user
   */
  async patchMe(
    requestParameters: PatchMeRequest,
    initOverrides?: RequestInit
  ): Promise<UserDto> {
    const response = await this.patchMeRaw(requestParameters, initOverrides);
    return await response.value();
  }
}