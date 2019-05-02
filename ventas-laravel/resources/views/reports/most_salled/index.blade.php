@extends('layouts.admin')

@section('title', trans('reports.most_salled_summary'))

@section('new_button')
<!--<span class="new-button"><a href="{{ url('reports/profit-loss') }}?print=1&status={{ request('status') }}&year={{ request('year', $this_year) }}" target="_blank" class="btn btn-success btn-sm"><span class="fa fa-print"></span> &nbsp;{{ trans('general.print') }}</a></span>
-->
@endsection

@section('content')
<!-- Default box -->
<div class="box box-success">
    <div class="box-header with-border">
        {!! Form::open(['url' => 'reports/salled', 'role' => 'form', 'method' => 'GET']) !!}
        <div id="items" class="pull-left">
            {{ Form::date('desde', Carbon\Carbon::now(),['class' => 'form-control input-filter-date input-sm']) }}
            {{ Form::date('hasta', Carbon\Carbon::now(),['class' => 'form-control input-filter-date input-sm']) }}
            {!! Form::button('<span class="fa fa-filter"></span> &nbsp;' . trans('general.filter'), ['type' => 'submit', 'class' => 'btn btn-sm btn-default btn-filter']) !!}
        </div>
        {!! Form::close() !!}
    </div>
    <div class="table table-responsive table-report">
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th>Producto</th>
            <th>Descripcion</th>
            <th>Cantidad</th>
          </tr>
        </thead>
        <tbody>
          @foreach ($data as $key => $value)
            <tr>
              <td>{{$value->product}}</td>
              <td>{{$value->description}}</td>
              <td>{{$value->cantidad_vendido}}</td>
            </tr>
          @endforeach
        </tbody>
      </table>
    </div>
</div>
<!-- /.box -->
@endsection
